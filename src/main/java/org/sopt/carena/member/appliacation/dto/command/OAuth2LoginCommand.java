package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.domain.AuthType;
/**
 * OAuth2 로그인 커맨드
 */
public record OAuth2LoginCommand(
        AuthType authType,
        String providerUserId,
        String email
) {
    public static OAuth2LoginCommand of(
            AuthType authType,
            String providerUserId,
            String email
    ) {
        return new OAuth2LoginCommand(authType, providerUserId, email);
    }
}