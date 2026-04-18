package com.barrioapp.api_padre.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * LowStockRequest class
 *
 * @Version: 1.0.0 - 17 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 17 abr. 2026
 */
@Getter
@Setter
public class LowStockRequest {

    private Long userId;
    private String productName;
    private Integer currentStock;
    private Integer minStock;
}
