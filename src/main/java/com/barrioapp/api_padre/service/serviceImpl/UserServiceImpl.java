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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl class
 *
 * @Version: 1.0.1 - 12 abr. 2026
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

        String token = jwtService.generateToken(saved);
        sessionService.saveSession(saved.getId(), token);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getCompanyName(),
                saved.getPlan().getType().name(),
                token
        );
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Incorrect credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect credentials");
        }

        String token = jwtService.generateToken(user);
        sessionService.saveSession(user.getId(), token);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getCompanyName(),
                user.getPlan().getType().name(),
                token
        );
    }
}
