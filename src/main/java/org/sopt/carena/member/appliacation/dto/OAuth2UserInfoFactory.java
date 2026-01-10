package org.sopt.carena.member.appliacation.dto;

import org.sopt.carena.member.adapter.out.external.oauth.KakaoOAuth2UserInfo;
import org.sopt.carena.member.adapter.out.external.oauth.KakaoOidcUserInfo;
import org.sopt.carena.member.appliacation.exception.oauth.UnsupportedOAuthProviderException;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OAuth2 제공자에 따라 UserInfo 구현체 생성
 */
@Component
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(
            AuthType authType,
            Map<String, Object> attributes
    ) {
        return switch (authType) {
            case KAKAO -> createKakaoUserInfo(attributes);
            default -> throw new UnsupportedOAuthProviderException();
        };
    }

    private OAuth2UserInfo createKakaoUserInfo(
            Map<String, Object> attributes
    ) {
        if (isOidc(attributes)) {
            return new KakaoOidcUserInfo(attributes);
        }
        return new KakaoOAuth2UserInfo(attributes);
    }

    private boolean isOidc(Map<String, Object> attributes) {
        // OIDC 필수 claim
        return attributes.containsKey("sub");
    }
}
