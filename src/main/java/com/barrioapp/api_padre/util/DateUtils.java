package com.barrioapp.api_padre.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * DateUtils class
 *
 * @Version: 1.0.0 - 18 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 18 abr. 2026
 */
public class DateUtils {

    private DateUtils() {}

    public static String currentMonthPeriod() {
        String period = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES")));
        return period.substring(0, 1).toUpperCase() + period.substring(1);
    }
}
