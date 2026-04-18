package com.barrioapp.api_padre.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * NotificationResponse class
 *
 * @Version: 1.0.0 - 16 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 16 abr. 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String message;
    private String type;
    private Boolean read;
    private LocalDateTime date;
}
