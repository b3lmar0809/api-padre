package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * SessionServiceImpl class
 *
 * @Version: 1.0.0 - 12 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 12 abr. 2026
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "session:";
    private static final long TTL_HORAS = 24;

    @Override
    public void saveSession(Long userId, String token) {
        redisTemplate.opsForValue().set(PREFIX + userId, token, TTL_HORAS, TimeUnit.HOURS);
    }

    @Override
    public void deleteSession(Long userId) {
        redisTemplate.delete(PREFIX + userId);
    }

    @Override
    public boolean sessionExists(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + userId));
    }
}
