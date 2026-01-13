package org.sopt.carena.member.appliacation.port.out;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenStore {

    void save(Long memberId, String refreshToken);
    Optional<String> get(Long memberId);
    void delete(Long memberId);
}