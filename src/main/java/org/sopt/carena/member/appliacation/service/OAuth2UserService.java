package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.sopt.carena.member.domain.OAuth2UserInfo;
//import org.sopt.carena.member.appliacation.dto.OAuth2UserInfoFactory;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.domain.AuthType;
//import org.sopt.carena.member.domain.CustomOAuth2User;
import org.sopt.carena.member.domain.Member;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring OAuth2가 사용자 정보를 가져온 후 호출하는 서비스
 *
 * 1. Spring이 가져온 사용자 정보를 우리 시스템에 맞게 변환
 * 2. 회원 존재 여부 확인
 * 3. CustomOAuth2User로 반환 (신규/기존 회원 정보 포함)
 */
/*
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        // 1. Spring이 이미 OAuth 제공자에서 사용자 정보를 가져왔음
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 어떤 제공자인지 확인 (kakao, naver 등)
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase();

        AuthType authType = AuthType.valueOf(registrationId);

        log.info("OAuth2 로그인 시도 - Provider: {}", authType);

        // 3. 제공자별 사용자 정보 추출
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                authType,
                oAuth2User.getAttributes()
        );

        String authId = userInfo.getProviderId();
        log.info("사용자 식별자 추출 - AuthType: {}, AuthId: {}", authType, authId);

        // 4. 회원 조회
        Optional<Member> memberOpt = memberRepository
                .findByAuthIdAndAuthType(authId, authType);

        // 5. CustomOAuth2User 생성 및 반환
        return new CustomOAuth2User(
                memberOpt.orElse(null),  // 기존 회원 또는 null
                authId,
                authType,
                oAuth2User.getAttributes()
        );
    }
}

 */

