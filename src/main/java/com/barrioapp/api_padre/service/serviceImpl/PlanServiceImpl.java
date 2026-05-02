package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.dto.PlanResponse;
import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.model.Plan;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.PlanRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.JwtService;
import com.barrioapp.api_padre.service.PlanService;
import com.barrioapp.api_padre.service.SessionService;
import com.barrioapp.api_padre.util.AuthUtils;
import com.barrioapp.api_padre.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PlanServiceImpl class
 *
 * @Version: 1.0.2 - 26 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 13 abr. 2026
 */
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SessionService sessionService;

    @Override
    public List<PlanResponse> planList() {
        return planRepository.findAll().stream()
                .map(p -> new PlanResponse(p.getId(), p.getType().name(), p.getLimitProduct(), p.getPrice()))
                .toList();
    }

    @Override
    public PlanResponse getPlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        return new PlanResponse(plan.getId(), plan.getType().name(), plan.getLimitProduct(), plan.getPrice());
    }

    @Override
    public PlanResponse getPlanByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Plan plan = user.getPlan();
        return new PlanResponse(plan.getId(), plan.getType().name(), plan.getLimitProduct(), plan.getPrice());
    }

    @Override
    public UserResponse changePlan(Long userId, Long planId) {
        if (userId == null || planId == null) {
            throw new RuntimeException("userId and planId are required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        user.setPlan(plan);
        userRepository.save(user);

        String token = AuthUtils.generateAndSaveToken(user, jwtService, sessionService);
        return UserMapper.toResponse(user, token);
    }
}
