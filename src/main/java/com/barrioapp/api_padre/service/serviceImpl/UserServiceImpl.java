package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.dto.LoginRequest;
import com.barrioapp.api_padre.dto.RegisterRequest;
import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.model.Plan;
import com.barrioapp.api_padre.model.PlanType;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.PlanRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.JwtService;
import com.barrioapp.api_padre.service.NotificationService;
import com.barrioapp.api_padre.service.SessionService;
import com.barrioapp.api_padre.service.UserService;
import com.barrioapp.api_padre.util.AuthUtils;
import com.barrioapp.api_padre.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl class
 *
 * @Version: 1.0.2 - 19 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final JwtService jwtService;
    private final SessionService sessionService;
    private final NotificationService notificationService;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Plan planFree = planRepository.findByType(PlanType.FREE)
                .orElseThrow(() -> new RuntimeException("Free plan not found"));

        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompanyName(request.getCompanyName());
        user.setRut(request.getRut());
        user.setPlan(planFree);

        User saved = userRepository.save(user);
        notificationService.sendWelcome(saved);

        String token = AuthUtils.generateAndSaveToken(saved, jwtService, sessionService);
        return UserMapper.toResponse(saved, token);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Incorrect credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect credentials");
        }

        String token = AuthUtils.generateAndSaveToken(user, jwtService, sessionService);
        return UserMapper.toResponse(user, token);
    }
}
