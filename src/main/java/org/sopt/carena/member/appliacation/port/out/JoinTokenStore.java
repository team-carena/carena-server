package org.sopt.carena.member.appliacation.port.out;

import java.time.Duration;
import java.util.Optional;

public interface JoinTokenStore {
    void save(String token, String authId, Duration ttl);
    Optional<String> getAuthId(String token);
    void delete(String token);
}