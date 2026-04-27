package com.barrioapp.api_padre.controller;

import com.barrioapp.api_padre.dto.LowStockRequest;
import com.barrioapp.api_padre.dto.NotificationResponse;
import com.barrioapp.api_padre.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NotificationController class
 *
 * @Version: 1.0.0 - 17 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 17 abr. 2026
 */
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotification(userId));
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<Long> countUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/low-stock")
    public ResponseEntity<Void> lowStockAlert(@Valid @RequestBody LowStockRequest request) {
        notificationService.sendLowStockAlert(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
