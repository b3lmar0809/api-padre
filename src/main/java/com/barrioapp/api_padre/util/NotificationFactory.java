package com.barrioapp.api_padre.util;

import com.barrioapp.api_padre.model.Notification;
import com.barrioapp.api_padre.model.NotificationType;
import com.barrioapp.api_padre.model.User;

/**
 * NotificationFactory class
 *
 * @Version: 1.0.0 - 18 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 18 abr. 2026
 */
public class NotificationFactory {

    private NotificationFactory() {}

    public static Notification create(User user, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setType(type);
        return notification;
    }
}
