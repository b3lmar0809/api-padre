package com.barrioapp.api_padre.controller;

import com.barrioapp.api_padre.dto.FlowPaymentResponse;
import com.barrioapp.api_padre.dto.FlowSubscribeRequest;
import com.barrioapp.api_padre.dto.PlanResponse;
import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.service.FlowService;
import com.barrioapp.api_padre.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * PlanController class
 *
 * @Version: 1.0.1 - Apr 22, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 13, 2026
 */
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final FlowService flowService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> listPlans() {
        return ResponseEntity.ok(planService.planList());
    }

    @PutMapping("/change")
    public ResponseEntity<UserResponse> changePlan(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long planId = body.get("planId");
        return ResponseEntity.ok(planService.changePlan(userId, planId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<FlowPaymentResponse> subscribe(@RequestBody @Valid FlowSubscribeRequest request) {
        return ResponseEntity.ok(flowService.createPayment(request.getUserId(), request.getPlanId()));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestParam Map<String, String> params) {
        flowService.processWebhook(params);
        return ResponseEntity.ok().build();
    }
}
