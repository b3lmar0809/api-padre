package com.barrioapp.api_padre.util;

import com.barrioapp.api_padre.dto.UserResponse;
import com.barrioapp.api_padre.model.User;

/**
 * UserMapper class
 *
 * @Version: 1.0.0 - 18 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 18 abr. 2026
 */
public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user, String token) {
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
