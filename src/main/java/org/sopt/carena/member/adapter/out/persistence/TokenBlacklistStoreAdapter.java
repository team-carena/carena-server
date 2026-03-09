package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.port.out.TokenBlacklistStore;
import org.sopt.carena.member.domain.TokenType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistStoreAdapter implements TokenBlacklistStore {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "blacklist:";

    @Override
    public void blacklist(String token, TokenType tokenType, long ttlMillis) {

        String key = PREFIX + tokenType.name().toLowerCase() + ":" + token;

        redisTemplate.opsForValue()
                .set(key, "blacklisted", Duration.ofMillis(ttlMillis));

        log.debug("{}Token 블랙리스트 등록 (TTL={}ms)", tokenType, ttlMillis);
    }

    @Override
    public boolean isBlacklisted(String token, TokenType tokenType) {

        String key = PREFIX + tokenType.name().toLowerCase() + ":" + token;

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
