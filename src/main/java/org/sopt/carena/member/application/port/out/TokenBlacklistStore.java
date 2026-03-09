package org.sopt.carena.member.application.port.out;

import org.sopt.carena.member.domain.TokenType;

public interface TokenBlacklistStore {
    void blacklist(String token, TokenType tokenType, long ttlMillis);

    boolean isBlacklisted(String token, TokenType tokenType);
}
