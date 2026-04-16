package com.barrioapp.api_padre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * FinanceRequest class
 *
 * @Version: 1.0.0 - 14 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 14 abr. 2026
 */
@Getter
@Setter
public class FinanceRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String type; // INCOME o EXPENSE

    @NotNull
    @Positive
    private Double amount;

    private String description;
}
