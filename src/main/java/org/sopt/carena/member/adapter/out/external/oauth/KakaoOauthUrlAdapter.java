/*
package org.sopt.carena.member.adapter.out.external.oauth;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.appliacation.port.out.OAuthUrlProviderPort;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.stereotype.Component;

*/
/**
 * 카카오 로그인 url생성
 *//*

@Component
@RequiredArgsConstructor
public class KakaoOauthUrlAdapter implements OAuthUrlProviderPort {

    private final KakaoOAuthProperties properties;  // 설정

    @Override
    public String getAuthorizationUrl(AuthType authType) {

        // URL 생성 로직은 Adapter에서 처리
        return String.format(
                "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=openid",
                properties.getClientId(),
                properties.getRedirectUri()
        );
    }
}*/
