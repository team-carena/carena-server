package org.sopt.carena.member.adapter.out.oauth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.in.web.dto.response.OAuth2AuthenticationResult;
import org.sopt.carena.member.application.dto.view.LoginSuccessView;
import org.sopt.carena.member.application.dto.view.NewMemberSignupView;
import org.sopt.carena.member.application.dto.view.OAuth2LoginResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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

        // OAuth2LoginResult 추출
        OAuth2LoginResult loginResult = authResult.getLoginResult();

        log.info("로그인 결과 - 신규회원: {}, Provider: {}",
                loginResult.needsSignup());

        // 신규/기존 회원 분기
        if (loginResult.needsSignup()) {
            handleNewMember(response, (NewMemberSignupView) loginResult);
        } else {
            handleExistingMember(response, (LoginSuccessView) loginResult);
        }
    }

    private void handleNewMember(
            HttpServletResponse response,
            NewMemberSignupView loginResult
    ) throws IOException {
        log.info("신규 회원 - 회원가입 페이지로 리다이렉트");
        // tempToken 쿠키에 저장
        addCookie(response, "tempToken", loginResult.tempToken(), 600);
        // 회원가입 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/login";
        response.sendRedirect(redirectUrl);
    }

    private void handleExistingMember(
            HttpServletResponse response,
            LoginSuccessView loginResult
    ) throws IOException {
        log.info("기존 회원 - 메인 페이지로 리다이렉트");
        //response.setHeader("Authorization", "Bearer " + loginResult.accessToken());
        addCookie(response, "oneTimeToken", loginResult.oneTimeToken(), 1209600);
        // 메인 페이지로 리다이렉트
        String redirectUrl = frontendUrl + "/oauth-callback";
        response.sendRedirect(redirectUrl);
    }
    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(maxAge)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
