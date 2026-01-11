/*
package org.sopt.carena.member.adapter.out.external.oauth;

import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.OAuth2UserInfo;

import java.util.Map;

public class KakaoOidcUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public KakaoOidcUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.KAKAO;
    }
}*/
