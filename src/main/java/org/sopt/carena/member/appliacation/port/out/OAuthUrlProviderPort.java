package org.sopt.carena.member.appliacation.port.out;

import org.sopt.carena.member.domain.AuthType;

/**
 * OAuth 제공자 Port
 */
public interface OAuthUrlProviderPort {
    /**
     * OAuth 인증 URL 생성
     * AuthType이 이미 검증된 상태로 들어옴
     */
    String getAuthorizationUrl(AuthType authType);
}
