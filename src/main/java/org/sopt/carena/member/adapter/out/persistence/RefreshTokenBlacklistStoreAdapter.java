package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.port.out.RefreshTokenBlacklistStore;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenBlacklistStoreAdapter implements RefreshTokenBlacklistStore {
    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "blacklist:";

    @Override
    public void blacklist(String refreshToken, long ttlMillis) {
        String key = PREFIX + refreshToken;
        redisTemplate.opsForValue()
                .set(key, "logout", Duration.ofMillis(ttlMillis));
        log.debug("RefreshToken 블랙리스트 등록 (TTL={}ms)", ttlMillis);
    }
}
