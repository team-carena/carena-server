package org.sopt.carena.member.adapter.out.external.oauth;

import lombok.AllArgsConstructor;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.OAuth2UserInfo;

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
    public AuthType getAuthType() {
        return AuthType.KAKAO;
    }
}
