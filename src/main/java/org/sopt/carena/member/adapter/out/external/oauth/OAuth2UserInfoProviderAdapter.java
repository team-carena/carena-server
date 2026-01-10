package org.sopt.carena.member.adapter.out.external.oauth;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.appliacation.dto.OAuth2UserInfoFactory;
import org.sopt.carena.member.appliacation.port.out.OAuth2UserInfoProvider;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OAuth2 사용자 정보 제공 어댑터
 */
@Component
@RequiredArgsConstructor
public class OAuth2UserInfoProviderAdapter implements OAuth2UserInfoProvider {

    private final OAuth2UserInfoFactory factory;

    @Override
    public OAuth2UserInfo getUserInfo(AuthType authType, Map<String, Object> attributes) {
        return factory.getOAuth2UserInfo(authType, attributes);
    }
}