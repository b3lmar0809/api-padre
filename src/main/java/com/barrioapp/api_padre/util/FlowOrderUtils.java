package com.barrioapp.api_padre.util;
/**
 * FlowOrderUtils class
 *
 * @Version: 1.0.0 - Apr 21, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 21, 2026
 */
public class FlowOrderUtils {

    private FlowOrderUtils() {}

    public static String generate(Long userId) {

        return "barrioapp-" + userId + "-" + System.currentTimeMillis();
    }

    public static Long extractUserId(String commerceOrder) {

        return Long.parseLong(commerceOrder.split("-")[1]);
    }
}
