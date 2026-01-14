package org.sopt.carena.member.application.port.out;

public interface AccessTokenBlacklistStore {
    void blacklist(String accessToken, long ttlMillis);
    boolean isBlacklisted(String accessToken);
}