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
        log.debug("Refresh Token 저장 완료 - memberId: {}, ttl: {}초", memberId, ttl.getSeconds());
    }

    @Override
    public Optional<String> get(Long memberId) {
        String key = KEY_PREFIX + memberId;
        String refreshToken = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void delete(Long memberId) {
        String key = KEY_PREFIX + memberId;
        redisTemplate.delete(key);
        log.debug("Refresh Token 삭제 완료 - memberId: {}", memberId);
    }

    @Override
    public boolean exists(Long memberId) {
        String key = KEY_PREFIX + memberId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}