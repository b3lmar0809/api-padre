package com.barrioapp.api_padre.service;

/**
 * EmailService class
 *
 * @Version: 1.0.0 - 17 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/17
 */

public interface EmailService {

    void sendEmail(String recipient, String matter, String contentHtml);
}
