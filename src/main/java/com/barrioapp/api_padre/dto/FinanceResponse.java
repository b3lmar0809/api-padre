package com.barrioapp.api_padre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * FinanceResponse class
 *
 * @Version: 1.0.0 - 14 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 14 abr. 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceResponse {

    private Long id;
    private String type;
    private Double amount;
    private String description;
    private LocalDateTime date;
}
