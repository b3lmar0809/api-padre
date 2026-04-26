package com.barrioapp.api_padre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * FlowWebhookRequest class
 *
 * @Version: 1.0.0 - Apr 20, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 20, 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowWebhookRequest {

    private String token;
    private String commerceOrder;
    private String flowOrder;
    private String subject;
    private Double amount;
    private String payer;
    private Integer status;
    private String paymentData;
}
