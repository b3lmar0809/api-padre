package com.barrioapp.api_padre.service;

import com.barrioapp.api_padre.dto.FlowPaymentResponse;

import java.util.Map;
/**
 * FlowService class
 *
 * @Version: 1.0.0 - Apr 21, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 21, 2026
 */
public interface FlowService {

    FlowPaymentResponse createPayment(Long userId, Long planId);

    void processWebhook(Map<String, String> params);
}
