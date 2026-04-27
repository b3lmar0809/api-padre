package com.barrioapp.api_padre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * ForgotPasswordRequest class
 *
 * @Version: 1.0.0 - 26 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 26 abr. 2026
 */
@Getter
@Setter
public class ForgotPasswordRequest {

    @Email
    @NotBlank
    private String email;
}
