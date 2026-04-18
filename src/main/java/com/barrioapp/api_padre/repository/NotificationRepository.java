package com.barrioapp.api_padre.repository;

import com.barrioapp.api_padre.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * NotificationRepository class
 *
 * @Version: 1.0.0 - 16 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/16
 */

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByDateDesc(Long userId);

    List<Notification> findByUserIdAndRead(Long userId, boolean reade);

    Long countByUserIdAndRead(Long userId, Boolean reade);
}
