package org.sopt.carena.member.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Spring Security의 OAuth2User를 구현한 커스텀 사용자 객체
 *
 * 역할:
 * - Spring Security가 인증 객체로 사용
 * - 우리의 Member 엔티티 정보 포함
 * - 신규/기존 회원 구분 가능
 */
@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Member member;  // null이면 신규 회원
    private final String authId;  // OAuth 제공자의 사용자 식별자
    private final AuthType authType;  // KAKAO 등
    private final Map<String, Object> attributes;

    public CustomOAuth2User(
            Member member,
            String authId,
            AuthType authType,
            Map<String, Object> attributes
    ) {
        this.member = member;
        this.authId = authId;
        this.authType = authType;
        this.attributes = attributes;
    }

    /**
     * 신규 회원인지 확인
     */
    public boolean isNewMember() {
        return member == null;
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
        return authId;
    }
}