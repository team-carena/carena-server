package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
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
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(14);

    @Override
    public void save(final long memberId, final String refreshToken) {
        String key = KEY_PREFIX + memberId;
        redisTemplate.opsForValue().set(key, refreshToken,REFRESH_TOKEN_TTL);
    }

    @Override
    public Optional<String> get(final long memberId) {
        String key = KEY_PREFIX + memberId;
        String refreshToken = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void delete(final long memberId) {
        String key = KEY_PREFIX + memberId;
        Boolean deleted = redisTemplate.delete(key);
        if (Boolean.TRUE.equals(deleted)) {
            log.debug("리프레시 토큰 삭제");
        } else {
            log.warn("리프레시 토큰 삭제실패");
        }
    }
}