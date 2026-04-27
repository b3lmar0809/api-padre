package com.barrioapp.api_padre.service;

/**
 * PasswordResetService class
 *
 * @Version: 1.0.0 - 26 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/26
 */

public interface PasswordResetService {

    void requestPasswordReset(String email);
    void resetPassword(String token, String newPassword);
}
