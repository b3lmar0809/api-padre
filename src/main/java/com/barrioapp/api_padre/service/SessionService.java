package com.barrioapp.api_padre.service;

/**
 * SesionService class
 *
 * @Version: 1.0.0 - 12 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 12 abr. 2026
 */

public interface SessionService {

    void saveSession(Long userId, String token);

    void deleteSession(Long userId);

    boolean sessionExists(Long userId);
}