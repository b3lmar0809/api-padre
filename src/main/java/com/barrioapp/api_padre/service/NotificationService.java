package com.barrioapp.api_padre.service;

import com.barrioapp.api_padre.dto.LowStockRequest;
import com.barrioapp.api_padre.dto.NotificationResponse;
import com.barrioapp.api_padre.model.User;
import lombok.extern.java.Log;

import java.util.List;

/**
 * NotificationService class
 *
 * @Version: 1.0.0 - 17 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/17
 */

public interface NotificationService {

    void sendWelcome(User user);
    void sendLowStockAlert(LowStockRequest request);
    void sendPlanUpdated(User user, String planType);
    List<NotificationResponse> getNotification(Long userId);
    void markAsRead(Long notificationId);
    Long countUnreadNotifications(Long userId);

}
