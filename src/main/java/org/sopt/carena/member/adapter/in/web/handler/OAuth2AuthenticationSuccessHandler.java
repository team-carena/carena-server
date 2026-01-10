package org.sopt.carena.member.adapter.in.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.in.web.OAuth2AuthenticationResult;
import org.sopt.carena.member.adapter.in.web.controller.BaseController;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2 인증 성공 핸들러
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        // 1. 애플리케이션 결과 추출
        OAuth2AuthenticationResult authResult =
                (OAuth2AuthenticationResult) authentication.getPrincipal();
        OAuth2LoginView loginResult = authResult.getLoginResult();

        // 2. HTTP 응답 처리
        if (loginResult.needsSignup()) {
            handleNewMember(response, loginResult);
        } else {
            handleExistingMember(response, loginResult);
        }
    }

    private void handleNewMember(
            HttpServletResponse response,
            OAuth2LoginView loginResult
    ) throws IOException {

        log.info("신규 회원 - 회원가입 페이지로 리다이렉트");

        // tempToken 쿠키에 저장
        BaseController.addTempTokenCookie(response, loginResult.tempToken());

        // 리다이렉트
        String redirectUrl = frontendUrl + "/signup";
        response.sendRedirect(redirectUrl);
    }

    private void handleExistingMember(
            HttpServletResponse response,
            OAuth2LoginView loginResult
    ) throws IOException {

        log.info("기존 회원 - 메인 페이지로 리다이렉트");

        // 토큰을 쿠키에 저장
        BaseController.addRefreshTokenCookie(response, loginResult.refreshToken());

        // 리다이렉트
        String redirectUrl = frontendUrl + "/";
        response.sendRedirect(redirectUrl);
    }
}