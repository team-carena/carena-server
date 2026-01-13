package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.domain.AuthType;

public record OAuth2LoginCommand(
        AuthType authType,
        String providerUserId
) {
    public static OAuth2LoginCommand of(
            final AuthType authType,
            final String providerUserId
    ) {
        return new OAuth2LoginCommand(authType, providerUserId);
    }
}