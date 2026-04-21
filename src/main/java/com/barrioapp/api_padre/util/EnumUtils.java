package com.barrioapp.api_padre.util;

/**
 * EnumUtils class
 *
 * @Version: 1.0.0 - 18 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 18 abr. 2026
 */
public class EnumUtils {

    private EnumUtils() {}

    public static <E extends Enum<E>> E parse(Class<E> enumClass, String value, String errorMessage) {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(errorMessage);
        }
    }
}
