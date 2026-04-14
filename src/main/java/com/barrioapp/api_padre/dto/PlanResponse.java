package com.barrioapp.api_padre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PlanResponse class
 *
 * @Version: 1.0.0 - 13 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 13 abr. 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {

    private Long id;
    private String type;
    private Integer limitProduct;
    private  Double price;
}
