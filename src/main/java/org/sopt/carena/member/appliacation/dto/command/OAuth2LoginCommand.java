package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.domain.AuthType;
import java.util.Map;

/**
 * OAuth2 로그인 커맨드
 */
public record OAuth2LoginCommand(
        AuthType authType,
        Map<String, Object> attributes
) {
    public static OAuth2LoginCommand of(AuthType authType, Map<String, Object> attributes) {
        return new OAuth2LoginCommand(authType, attributes);
    }
}