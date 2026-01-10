package org.sopt.carena.member.adapter.in.web;

import lombok.Getter;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Spring Security와 애플리케이션 결과를 연결하는 DTO
 */
@Getter
public class OAuth2AuthenticationResult implements OAuth2User {

    private final OAuth2LoginView loginResult;
    private final Map<String, Object> attributes;

    public OAuth2AuthenticationResult(
            OAuth2LoginView loginResult,
            Map<String, Object> attributes
    ) {
        this.loginResult = loginResult;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return loginResult.member() != null
                ? loginResult.member().id().toString()
                : "anonymous";
    }
}
