package org.sopt.carena.member.application.port.out;

public interface RefreshTokenBlacklistStore {
    void blacklist(String refreshToken, long ttlMillis);
}
