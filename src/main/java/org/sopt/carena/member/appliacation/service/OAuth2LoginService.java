package org.sopt.carena.member.appliacation.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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
    private final JoinTokenStore joinTokenStore;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2LoginView processLogin(OAuth2LoginCommand command) {

        log.info("OAuth2 로그인 처리 시작 - Provider: {}", command.authType());
        // provider + providerUserId 기준 회원 조회
        return memberRepository
                .findByAuthTypeAndProviderUserId(
                        command.authType(),
                        command.providerUserId()
                )
                .map(member -> handleExistingMember(member))
                .orElseGet(() -> handleNewMember(command));
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

    private OAuth2LoginView handleNewMember(OAuth2LoginCommand command) {
        log.info("신규 회원 - 회원가입 필요");

        // 임시 토큰 생성 및 저장
        String tempToken = UUID.randomUUID().toString();
        String authInfo = String.join("|",
                command.authType().name(),
                command.providerUserId()
        );

        joinTokenStore.save(
                tempToken,
                authInfo,
                Duration.ofMinutes(10)
        );
        return OAuth2LoginView.forNewMember(tempToken);
    }
}