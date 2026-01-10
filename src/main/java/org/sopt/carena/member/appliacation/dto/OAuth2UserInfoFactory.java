package org.sopt.carena.member.appliacation.dto;

import org.sopt.carena.member.domain.AuthType;

import java.util.Map;

/**
 * OAuth2 제공자에 따라 적절한 UserInfo 구현체 생성
 */
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(
            AuthType authType,
            Map<String, Object> attributes
    ) {
        return switch (authType) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);

            default -> throw new IllegalArgumentException(
                    "지원하지 않는 OAuth 제공자입니다: " + authType
            );
        };
    }
}
