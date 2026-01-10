package org.sopt.carena.member.adapter.in.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.in.web.controller.BaseController;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.appliacation.service.JwtTokenProvider;
import org.sopt.carena.member.domain.CustomOAuth2User;
import org.sopt.carena.member.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * OAuth2 로그인 성공 후 처리
 *
 * 역할:
 * 1. 기존 회원 → JWT 발급 + 메인 페이지 리다이렉트
 * 2. 신규 회원 → tempToken 발급 + 회원가입 페이지 리다이렉트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;
    private final JoinTokenStore joinTokenStore;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        if (oAuth2User.isNewMember()) {
            // 신규 회원 처리
            handleNewMember(request, response, oAuth2User);
        } else {
            // 기존 회원 처리
            handleExistingMember(request, response, oAuth2User);
        }
    }

    /**
     * 신규 회원: tempToken 발급 + 회원가입 페이지
     */
    private void handleNewMember(
            HttpServletRequest request,
            HttpServletResponse response,
            CustomOAuth2User oAuth2User
    ) throws IOException {

        log.info("신규 회원 - Provider: {}, AuthId: {}",
                oAuth2User.getAuthType(),
                oAuth2User.getAuthId());

        // tempToken 생성 및 Redis 저장 (10분)
        String tempToken = UUID.randomUUID().toString();
        joinTokenStore.save(
                tempToken,
                oAuth2User.getAuthId(),
                Duration.ofMinutes(10)
        );

        log.info("TempToken 생성 완료 - Token: {}", tempToken);

        // tempToken을 HttpOnly 쿠키에 저장
        BaseController.addTempTokenCookie(response, tempToken);

        // 회원가입 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/signup";
        log.info("회원가입 페이지로 리다이렉트: {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    /**
     * 기존 회원: JWT 발급 + 메인 페이지
     */
    private void handleExistingMember(
            HttpServletRequest request,
            HttpServletResponse response,
            CustomOAuth2User oAuth2User
    ) throws IOException {

        Member member = oAuth2User.getMember();

        log.info("기존 회원 로그인 - MemberId: {}, AuthType: {}",
                member.getId(),
                member.getAuthType());

        // JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // Refresh Token을 Redis에 저장 (14일)
        refreshTokenStore.save(
                member.getId(),
                refreshToken,
                Duration.ofDays(14)
        );

        log.info("JWT 발급 완료 - MemberId: {}", member.getId());

        // Refresh Token을 HttpOnly 쿠키에 저장
        BaseController.addRefreshTokenCookie(response, refreshToken);

        // Access Token을 쿼리 파라미터로 전달
        String redirectUrl = String.format(
                "%s/?accessToken=%s",
                frontendUrl,
                accessToken
        );

        log.info("메인 페이지로 리다이렉트: {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}