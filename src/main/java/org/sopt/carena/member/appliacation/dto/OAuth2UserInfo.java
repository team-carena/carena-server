package org.sopt.carena.member.appliacation.dto;


import java.util.Map;

/**
 * OAuth2 제공자별 사용자 정보 추상화
 * 카카오, 네이버 등 제공자마다 응답 구조가 다르므로 인터페이스로 통일
 */
public interface OAuth2UserInfo {

    /**
     * OAuth 제공자의 사용자 식별자
     * 카카오: sub (OIDC) 또는 id
     */
    String getProviderId();

    /**
     * 이메일
     */
    String getEmail();

    /**
     * 닉네임
     */
    String getNickname();

    /**
     * 원본 속성
     */
    Map<String, Object> getAttributes();
}
