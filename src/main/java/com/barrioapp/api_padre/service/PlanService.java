package com.barrioapp.api_padre.service;

import com.barrioapp.api_padre.dto.PlanResponse;
import com.barrioapp.api_padre.dto.UserResponse;

import java.util.List;

/**
 * PlanService class
 *
 * @Version: 1.0.0 - 13 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/13
 */

public interface PlanService {
    List<PlanResponse> planList();
    PlanResponse  getPlan(Long id);
    UserResponse changePlan(Long userId, Long PlanId);
}
