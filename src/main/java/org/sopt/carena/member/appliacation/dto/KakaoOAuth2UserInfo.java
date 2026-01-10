package org.sopt.carena.member.appliacation.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 카카오 OAuth2 사용자 정보
 */
@AllArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        // 카카오- id 필드에 식별자 존재
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.get("kakao_account");

        if (kakaoAccount == null) {
            return null;
        }

        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties =
                (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
