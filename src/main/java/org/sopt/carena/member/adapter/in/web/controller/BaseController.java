package org.sopt.carena.member.adapter.in.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


public abstract class BaseController {
    private static final int TEMP_TOKEN_MAX_AGE = 600;

    /**
     * 쿠키 추가 (공통 메서드)
     */
    protected void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
    }

    /**
     * Access Token 쿠키 추가
     */
    protected void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        addCookie(response, "accessToken", accessToken, 3600);  // 1시간
    }

    /**
     * Refresh Token 쿠키 추가
     */
    protected void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        addCookie(response, "refreshToken", refreshToken, 1209600);  // 14일
    }

    /**
     * 인증 토큰 쿠키 추가 (Access + Refresh)
     */
    protected void addAuthTokenCookies(
            HttpServletResponse response,
            String accessToken,
            String refreshToken
    ) {
        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }

    protected void addTempTokenCookie(HttpServletResponse response, String tempToken) {
        addCookie(response, "tempToken", tempToken, TEMP_TOKEN_MAX_AGE);
    }

    /**
     * 쿠키 삭제
     */

}