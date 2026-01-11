package org.sopt.carena.member.adapter.in.web.dto;

import lombok.Getter;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;

/**
 * Spring Security와 애플리케이션 결과를 연결하는 DTO
 */
@Getter
public class OAuth2AuthenticationResult implements OidcUser {

    private final OidcUser delegate;
    private final OAuth2LoginView loginResult;

    public OAuth2AuthenticationResult(OidcUser delegate, OAuth2LoginView loginResult) {
        this.delegate = delegate;
        this.loginResult = loginResult;
    }

    // === OidcUser 인터페이스 위임 ===
    @Override public Map<String, Object> getAttributes() { return delegate.getAttributes(); }
    @Override public Map<String, Object> getClaims() { return delegate.getClaims(); }
    @Override public OidcUserInfo getUserInfo() { return delegate.getUserInfo(); }
    @Override public OidcIdToken getIdToken() { return delegate.getIdToken(); }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return delegate.getAuthorities(); }
    @Override public String getName() { return delegate.getName(); }
}

