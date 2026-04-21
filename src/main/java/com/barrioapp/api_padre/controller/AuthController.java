package com.barrioapp.api_padre.controller;

import com.barrioapp.api_padre.dto.LoginRequest;
import com.barrioapp.api_padre.dto.RegisterRequest;
import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.service.JwtService;
import com.barrioapp.api_padre.service.SessionService;
import com.barrioapp.api_padre.service.UserService;
import com.barrioapp.api_padre.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * AuthController class
 *
 * @Version: 1.0.2 - 19 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SessionService sessionService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request,
                                                 HttpServletResponse response) {
        UserResponse result = userService.register(request);
        addTokenCookie(response, result.getToken());
        result.setToken(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        UserResponse result = userService.login(request);
        addTokenCookie(response, result.getToken());
        result.setToken(null);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(c -> "token".equals(c.getName()))
                    .map(Cookie::getValue)
                    .filter(jwtService::isValid)
                    .map(jwtService::extractUserId)
                    .findFirst()
                    .ifPresent(sessionService::deleteSession);
        }

        CookieUtils.clearTokenCookie(response);
        return ResponseEntity.ok(Map.of("message", "Log out"));
    }

    private void addTokenCookie(HttpServletResponse response, String token) {
        CookieUtils.addTokenCookie(response, token);
    }
}
