package com.barrioapp.api_padre.service;

import com.barrioapp.api_padre.dto.LoginRequest;
import com.barrioapp.api_padre.dto.RegisterRequest;
import com.barrioapp.api_padre.dto.UserResponse;

/**
 * UserService interface
 *
 * @Version: 1.0.0 - 11 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse login(LoginRequest request);
}
