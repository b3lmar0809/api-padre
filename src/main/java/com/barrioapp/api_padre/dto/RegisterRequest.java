package com.barrioapp.api_padre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * RegisterRequest class
 *
 * @Version: 1.0.0 - 11 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "The email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8)
    private String password;

    @NotBlank(message = "company name is required")
    private String companyName;

    @NotBlank(message = "rut is required")
    private String rut;
}
