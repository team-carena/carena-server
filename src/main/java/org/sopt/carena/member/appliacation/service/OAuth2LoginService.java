package org.sopt.carena.member.appliacation.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.*;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.MemberPersistencePort;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.appliacation.service.util.JwtTokenGenerator;
import org.sopt.carena.member.domain.Member;
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
@Transactional(readOnly = true)
public class OAuth2LoginService implements OAuth2LoginUseCase {

    private final MemberPersistencePort memberPersistencePort;
    private final JoinTokenStore joinTokenStore;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public OAuth2LoginResult processLogin(final OAuth2LoginCommand command) {
        log.info("OAuth2 로그인 처리 시작 - Provider: {}", command.authType());
        Optional<Member> memberOpt = memberPersistencePort.findByAuthTypeAndProviderUserId(
                command.authType(),
                command.providerUserId()
        );
        if (memberOpt.isPresent()) {
            return handleExistingMember(memberOpt.get());
        } else {
            return handleNewMember(command);
        }

    }

    private LoginSuccessView handleExistingMember(final Member member) {
        log.info("기존 회원 로그인 - MemberId: {}", member.getId());

        String accessToken = jwtTokenGenerator.createAccessToken(member.getId());
        String refreshToken = jwtTokenGenerator.createRefreshToken(member.getId());

        // Refresh Token 저장
        refreshTokenStore.save(
                member.getId(),
                refreshToken
        );
        return LoginSuccessView.of(
                accessToken,
                refreshToken,
                MemberView.from(member)
        );
    }

    private NewMemberSignupView handleNewMember(final OAuth2LoginCommand command) {
        log.info("신규 회원 - 회원가입 필요");

        // 임시 토큰 생성 및 저장
        String tempToken = UUID.randomUUID().toString();
        String authInfo = String.join("|", command.authType().name(), command.providerUserId());

        joinTokenStore.save(
                tempToken,
                authInfo,
                Duration.ofMinutes(10)
        );
        return NewMemberSignupView.of(tempToken);
    }
}