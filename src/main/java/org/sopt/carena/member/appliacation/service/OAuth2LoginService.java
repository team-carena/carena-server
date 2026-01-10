package org.sopt.carena.member.appliacation.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.appliacation.port.out.OAuth2UserInfoProvider;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.domain.OAuth2UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

/**
 * OAuth2 로그인 애플리케이션 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2LoginService implements OAuth2LoginUseCase {

    private final MemberRepository memberRepository;
    private final OAuth2UserInfoProvider oauth2UserInfoProvider;
    private final JoinTokenStore joinTokenStore;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2LoginView processLogin(OAuth2LoginCommand command) {

        log.info("OAuth2 로그인 처리 시작 - Provider: {}", command.authType());

        // 1. OAuth2 속성을 도메인 객체로 변환
        OAuth2UserInfo userInfo = oauth2UserInfoProvider.getUserInfo(
                command.authType(),
                command.attributes()
        );

        String authId = userInfo.getProviderId();
        AuthType authType = userInfo.getAuthType();

        log.info("사용자 정보 추출 완료 - AuthId: {}", authId);

        // 2. 회원 조회
        Optional<Member> memberOpt = memberRepository
                .findByAuthIdAndAuthType(authId, authType);

        if (memberOpt.isPresent()) {
            // 기존 회원
            return handleExistingMember(memberOpt.get());
        } else {
            // 신규 회원
            return handleNewMember(authId);
        }
    }

    private OAuth2LoginView handleExistingMember(Member member) {
        log.info("기존 회원 로그인 - MemberId: {}", member.getId());

        // JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // Refresh Token 저장
        refreshTokenStore.save(
                member.getId(),
                refreshToken,
                Duration.ofDays(14)
        );

        return OAuth2LoginView.forExistingMember(
                accessToken,
                refreshToken,
                MemberView.from(member)
        );
    }

    private OAuth2LoginView handleNewMember(String authId) {
        log.info("신규 회원 - 회원가입 필요");

        // 임시 토큰 생성 및 저장
        String tempToken = UUID.randomUUID().toString();
        joinTokenStore.save(tempToken, authId, Duration.ofMinutes(10));

        return OAuth2LoginView.forNewMember(tempToken);
    }
}