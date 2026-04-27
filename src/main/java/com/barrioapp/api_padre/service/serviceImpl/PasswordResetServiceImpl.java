package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.model.PasswordResetToken;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.PasswordResetTokenRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.EmailService;
import com.barrioapp.api_padre.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * PasswordResetServiceImpl class
 *
 * @Version: 1.0.0 - 26 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 26 abr. 2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        //salida silenciosa si el correo electronico no está registrado — evita revelar correos electronicos registrados
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.info("Password reset requested for unregistered email: {}", email);
            return;
        }

        //invalidar cualquier token no usado previamente para este usuario
        passwordResetTokenRepository.invalidatePreviousTokens(user.getId());

        // Generate a unique token and save it with a 15-minute expiry
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        resetToken.setUsed(false);
        passwordResetTokenRepository.save(resetToken);

        //construir enlace de restablecimiento y enviar correo electronico
        String resetLink = frontendUrl + "/reset-password?token=" + token;

        String html = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #2d7a4f;">Restablecer Contraseña — BarrioApp</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.</p>
                    <p>Haz clic en el botón de abajo para crear una nueva contraseña:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s"
                           style="background-color: #2d7a4f; color: white; padding: 14px 28px;
                                  text-decoration: none; border-radius: 6px; font-size: 16px;">
                            Restablecer Contraseña
                        </a>
                    </div>
                    <p style="color: #666; font-size: 14px;">
                        Este enlace expira en <strong>15 minutos</strong>.
                    </p>
                    <p style="color: #666; font-size: 14px;">
                        Si no solicitaste este cambio, puedes ignorar este correo con seguridad.
                        Tu contraseña no será modificada.
                    </p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                    <p style="color: #999; font-size: 12px;">Equipo de BarrioApp</p>
                </div>
                """,
                user.getName(),
                resetLink
        );

        emailService.sendEmail(user.getEmail(), "Reset your password — BarrioApp", html);
        log.info("Password reset email sent to: {}", email);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (resetToken.isExpired()) {
            throw new RuntimeException("Token expired");
        }

        //actualizar la contraseña del usuario
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        //marcar el token como usado para que no pueda reutilizarse
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        log.info("Password successfully reset for user: {}", user.getEmail());
    }
}
