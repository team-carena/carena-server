package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenStoreAdapter implements RefreshTokenStore {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "refresh:";

    @Override
    public void save(Long memberId, String refreshToken, Duration ttl) {
        String key = KEY_PREFIX + memberId;
        redisTemplate.opsForValue().set(key, refreshToken, ttl);
    }

    @Override
    public Optional<String> get(Long memberId) {
        String key = KEY_PREFIX + memberId;
        String refreshToken = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(refreshToken);
    }
}