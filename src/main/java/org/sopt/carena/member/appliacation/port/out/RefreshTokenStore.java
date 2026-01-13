package org.sopt.carena.member.appliacation.port.out;

import java.util.Optional;

public interface RefreshTokenStore {

    void save(long memberId, String refreshToken);
    Optional<String> get(long memberId);
    void delete(long memberId);
}