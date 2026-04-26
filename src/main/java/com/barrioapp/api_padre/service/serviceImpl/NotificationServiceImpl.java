package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.dto.LowStockRequest;
import com.barrioapp.api_padre.dto.NotificationResponse;
import com.barrioapp.api_padre.model.Notification;
import com.barrioapp.api_padre.model.NotificationType;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.NotificationRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.EmailService;
import com.barrioapp.api_padre.service.NotificationService;
import com.barrioapp.api_padre.util.NotificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NotificationServiceImpl class
 *
 * @Version: 1.0.2 - 22 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 17 abr. 2026
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public void sendWelcome(User user) {
        String message = "¡Bienvenido a BarrioApp, " + user.getCompanyName() + "! Tu cuenta ha sido creada con el plan Free.";

        notificationRepository.save(NotificationFactory.create(user, message, NotificationType.WELCOME));

        String html = "<h2>¡Bienvenido a BarrioApp!</h2>" +
                "<p>Hola " + user.getName() + ", tu cuenta para <strong>" + user.getCompanyName() + "</strong>" +
                " ha sido creada exitosamente con el plan Free.</p>" +
                "<p>Ya puedes comenzar a gestionar tu almacén.</p>" +
                "<br><p>El equipo de BarrioApp</p>";

        emailService.sendEmail(user.getEmail(), "¡Bienvenido a BarrioApp!", html);
    }

    @Override
    public void sendLowStockAlert(LowStockRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String message = "⚠️ Stock bajo: " + request.getProductName() +
                " tiene solo " + request.getCurrentStock() +
                " unidades (mínimo: " + request.getMinStock() + ")";

        notificationRepository.save(NotificationFactory.create(user, message, NotificationType.LOW_STOCK));

        String html = "<h2>Alerta de stock bajo</h2>" +
                "<p>El producto <strong>" + request.getProductName() + "</strong>" +
                " tiene solo <strong>" + request.getCurrentStock() + " unidades</strong>.</p>" +
                "<p>Stock mínimo configurado: " + request.getMinStock() + " unidades.</p>" +
                "<p>Te recomendamos reabastecer pronto.</p>" +
                "<br><p>El equipo de BarrioApp</p>";

        emailService.sendEmail(user.getEmail(), " Alerta de stock bajo — BarrioApp", html);
    }

    @Override
    public void sendPlanUpdated(User user, String planType) {
        String message = "Tu plan ha sido actualizado a " + planType + " exitosamente.";
        notificationRepository.save(NotificationFactory.create(user, message, NotificationType.UPDATE_PLAN));

        String html = "<h2>Plan actualizado</h2>" +
                "<p>Hola " + user.getName() + ", tu plan ha sido actualizado a <strong>" + planType + "</strong> exitosamente.</p>" +
                "<p>Ya puedes disfrutar de los beneficios de tu nuevo plan.</p>" +
                "<br><p>El equipo de BarrioApp</p>";

        emailService.sendEmail(user.getEmail(), "Plan actualizado — BarrioApp", html);
    }

    @Override
    public List<NotificationResponse> getNotification(Long userId) {
        return notificationRepository.findByUserIdOrderByDateDesc(userId).stream()
                .map(n -> new NotificationResponse(
                        n.getId(),
                        n.getMessage(),
                        n.getType().name(),
                        n.getRead(),
                        n.getDate()
                ))
                .toList();
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public Long countUnreadNotifications(Long userId) {
        return notificationRepository.countByUserIdAndRead(userId, false);
    }
}
