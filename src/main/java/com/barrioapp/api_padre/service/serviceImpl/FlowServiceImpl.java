package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.config.FlowConfig;
import com.barrioapp.api_padre.dto.FlowPaymentResponse;
import com.barrioapp.api_padre.model.Plan;
import com.barrioapp.api_padre.model.Transaction;
import com.barrioapp.api_padre.model.TransactionStatus;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.PlanRepository;
import com.barrioapp.api_padre.repository.TransactionRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.FlowService;
import com.barrioapp.api_padre.service.NotificationService;
import com.barrioapp.api_padre.service.PlanService;
import com.barrioapp.api_padre.util.FlowOrderUtils;
import com.barrioapp.api_padre.util.FlowSignatureUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * FlowServiceImpl class
 *
 * @Version: 1.0.1 - Apr 26, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 21, 2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {

    private final FlowConfig flowConfig;
    private final PlanService planService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public FlowPaymentResponse createPayment(Long userId, Long planId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (plan.getPrice() == null || plan.getPrice() < 350) {
            throw new RuntimeException("Plan price too low for payment");
        }

        try {
            String commerceOrder = FlowOrderUtils.generate(userId);

            TreeMap<String, String> params = new TreeMap<>();
            params.put("apiKey",          flowConfig.getApiKey());
            params.put("amount",          String.valueOf(plan.getPrice().intValue()));
            params.put("commerceOrder",   commerceOrder);
            params.put("currency",        "CLP");
            params.put("email",           user.getEmail());
            params.put("subject",         "Plan Subscription " + plan.getType().name() + " - BarrioApp");
            params.put("urlConfirmation", flowConfig.getUrlConfirmation());
            params.put("urlReturn",       flowConfig.getUrlReturn());

            params.put("s", FlowSignatureUtils.sign(params, flowConfig.getSecretKey()));

            String responseJson = postFlow("/payment/create", params);
            String url   = extractJsonString(responseJson, "url");
            String token = extractJsonString(responseJson, "token");

            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setPlan(plan);
            transaction.setCommerceOrder(commerceOrder);
            transaction.setAmount(plan.getPrice());
            transaction.setStatus(TransactionStatus.PENDING);
            transactionRepository.save(transaction);

            log.info("Flow payment created — commerceOrder: {} — user: {}", commerceOrder, user.getEmail());
            return new FlowPaymentResponse(url, token);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Flow payment: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void processWebhook(Map<String, String> params) {
        if (!FlowSignatureUtils.verifySignature(params, flowConfig.getSecretKey())) {
            log.warn("Flow webhook rejected — invalid signature");
            throw new RuntimeException("Invalid signature");
        }

        String flowOrder     = params.getOrDefault("flowOrder", "");
        String commerceOrder = params.getOrDefault("commerceOrder", "");
        int status;
        try {
            status = Integer.parseInt(params.getOrDefault("status", "0"));
        } catch (NumberFormatException e) {
            log.warn("Flow webhook — invalid status value: {}", params.get("status"));
            status = 0;
        }

        if (transactionRepository.existsByFlowOrder(flowOrder)) {
            log.info("Flow webhook ignored — flowOrder {} already processed", flowOrder);
            return;
        }

        Transaction transaction = transactionRepository.findByCommerceOrder(commerceOrder)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setFlowOrder(flowOrder);

        if (status != 2) {
            transaction.setStatus(TransactionStatus.REJECTED);
            transactionRepository.save(transaction);
            log.info("Flow webhook — payment not successful, status={}, commerceOrder={}", status, commerceOrder);
            return;
        }

        transaction.setStatus(TransactionStatus.PAID);
        transactionRepository.save(transaction);

        Long userId = FlowOrderUtils.extractUserId(commerceOrder);
        planService.changePlan(userId, transaction.getPlan().getId());
        notificationService.sendPlanUpdated(transaction.getUser(), transaction.getPlan().getType().name());
    }

    private String extractJsonString(String json, String key) {
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"")
                .matcher(json);
        if (m.find()) return m.group(1).replace("\\/", "/");
        throw new RuntimeException("Field '" + key + "' not found in Flow response");
    }

    private String postFlow(String endpoint, TreeMap<String, String> params) throws Exception {
        List<NameValuePair> formParams = new ArrayList<>();
        params.forEach((k, v) -> formParams.add(new BasicNameValuePair(k, v)));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(flowConfig.getApiUrl() + endpoint);
            post.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

                if (statusCode < 200 || statusCode >= 300) {
                    throw new RuntimeException("Flow responded with error " + statusCode + ": " + body);
                }
                return body;
            }
        }
    }
}
