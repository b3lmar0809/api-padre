package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.service.EmailService;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * EmailServiceImpl class
 *
 * @Version: 1.0.0 - 17 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 17 abr. 2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Resend resend;

    @Value("${resend.from-email}")
    private String fromEmail;

    @Override
    public void sendEmail(String recipient, String matter, String contentHtml) {
        log.info("Sending email to: {} | from: {} | subject: {}", recipient, fromEmail, matter);
        try {
            CreateEmailOptions emailRequest = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(recipient)
                    .subject(matter)
                    .html(contentHtml)
                    .build();
            resend.emails().send(emailRequest);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", recipient, e.getMessage());
        }
    }

}
