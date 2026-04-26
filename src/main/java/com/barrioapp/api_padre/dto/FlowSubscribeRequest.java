package com.barrioapp.api_padre.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * FlowSubscribeRequest class
 *
 * @Version: 1.0.0 - Apr 20, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 20, 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowSubscribeRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long planId;
}
