package org.sopt.carena.member.adapter.out.external.kakao;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.OAuth2UserInfo;

import java.util.Map;

/**
 * 카카오 OAuth2 사용자 정보 구현체
 */
/*
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties =
                (Map<String, Object>) attributes.get("properties");
        return properties != null ? (String) properties.get("nickname") : null;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.KAKAO;
    }
}

 */