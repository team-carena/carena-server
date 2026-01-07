package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JoinTokenStoreAdapter implements JoinTokenStore {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "join:token:";

    @Override
    public void save(String token, String authId, Duration ttl) {
        String key = KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, authId, ttl);
    }

    @Override
    public Optional<String> getAuthId(String token) {
        String key = KEY_PREFIX + token;
        String authId = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(authId);
    }

    @Override
    public void delete(String token) {
        String key = KEY_PREFIX + token;
        redisTemplate.delete(key);
    }
}