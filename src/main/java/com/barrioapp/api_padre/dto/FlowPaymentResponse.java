package com.barrioapp.api_padre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FlowPaymentResponse class
 *
 * @Version: 1.0.0 - Apr 20, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 20, 2026
 */
@Getter
@Setter
@NoArgsConstructor
public class FlowPaymentResponse {

    private String url;
    private String token;
    private String paymentUrl;

    public FlowPaymentResponse(String url, String token) {
        this.url = url;
        this.token = token;
        this.paymentUrl = url + "?token=" + token;
    }
}
