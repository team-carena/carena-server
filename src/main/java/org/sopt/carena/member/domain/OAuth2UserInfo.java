package org.sopt.carena.member.domain;


import java.util.Map;

/**
 * OAuth2 제공자별 사용자 정보 추상화
 * 카카오, 네이버 등 제공자마다 응답 구조가 다르므로 인터페이스로 통일
 * Spring이나 외부 라이브러리에 의존하지 않아서 도메인 계층이라고 판단.
 */
public interface OAuth2UserInfo {

    /**
     * OAuth 제공자의 사용자 식별자
     * 카카오: sub (OIDC) 또는 id
     */
    String getProviderId();
    AuthType getAuthType();
}
