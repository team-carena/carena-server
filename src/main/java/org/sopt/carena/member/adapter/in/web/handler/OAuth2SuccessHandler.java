package org.sopt.carena.member.adapter.in.web.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.in.web.dto.OAuth2AuthenticationResult;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2 로그인 성공 후 처리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        log.info("=== OAuth2 인증 성공 후 핸들러 실행 ===");

        // OAuth2AuthenticationResult로 캐스팅
        OAuth2AuthenticationResult authResult =
                (OAuth2AuthenticationResult) authentication.getPrincipal();

        // OAuth2LoginView 추출
        OAuth2LoginView loginResult = authResult.getLoginResult();

        log.info("로그인 결과 - 신규회원: {}, Provider: {}",
                loginResult.needsSignup());

        // 신규/기존 회원 분기
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
        addCookie(response, "tempToken", loginResult.tempToken(), 600);

        // 회원가입 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/signup.html"; //수정예저에
        response.sendRedirect(redirectUrl);
    }

    private void handleExistingMember(
            HttpServletResponse response,
            OAuth2LoginView loginResult
    ) throws IOException {
        log.info("기존 회원 - 메인 페이지로 리다이렉트");
        // JWT 쿠키에 저장
        addCookie(response, "accessToken", loginResult.accessToken(), 3600);
        addCookie(response, "refreshToken", loginResult.refreshToken(), 1209600);
        // 메인 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/index.html"; //수정예정
        response.sendRedirect(redirectUrl);
    }
    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);  //  로컬에서만
        response.addCookie(cookie);
    }
}