package com.barrioapp.api_padre.controller;

import com.barrioapp.api_padre.dto.PlanResponse;
import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * PlanController class
 *
 * @Version: 1.0.0 - 13 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 13 abr. 2026
 */
@RestController
@RequestMapping("/planes")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> listarPlanes() {
        return ResponseEntity.ok(planService.planList());
    }

    @PutMapping("/cambiar")
    public ResponseEntity<UserResponse> cambiarPlan(@RequestBody Map<String, Long> body) {
        Long usuarioId = body.get("usuarioId");
        Long planId = body.get("planId");
        return ResponseEntity.ok(planService.changePlan(usuarioId, planId));
    }
}
