package com.barrioapp.api_padre.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * FlowConfig class
 *
 * @Version: 1.0.0 - Apr 20, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 20, 2026
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "flow")
public class FlowConfig {

    private String apiKey;
    private String secretKey;
    private String apiUrl;
    private String urlConfirmation;
    private String urlReturn;
}
