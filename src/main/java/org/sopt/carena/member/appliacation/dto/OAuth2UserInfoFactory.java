package org.sopt.carena.member.appliacation.dto;

import org.sopt.carena.member.adapter.out.external.oauth.KakaoOAuth2UserInfo;
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
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);

            default -> throw new UnsupportedOAuthProviderException();
        };
    }
}
