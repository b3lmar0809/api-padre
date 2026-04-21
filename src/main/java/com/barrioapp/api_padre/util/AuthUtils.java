package com.barrioapp.api_padre.util;

import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.service.JwtService;
import com.barrioapp.api_padre.service.SessionService;

/**
 * AuthUtils class
 *
 * @Version: 1.0.0 - 18 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 18 abr. 2026
 */
public class AuthUtils {

    private AuthUtils() {}

    public static String generateAndSaveToken(User user, JwtService jwtService, SessionService sessionService) {
        String token = jwtService.generateToken(user);
        sessionService.saveSession(user.getId(), token);
        return token;
    }
}
