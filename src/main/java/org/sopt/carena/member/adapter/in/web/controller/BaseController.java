package org.sopt.carena.member.adapter.in.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


public abstract class BaseController {
    private static final int TEMP_TOKEN_MAX_AGE = 600;
    private static final int REFRESH_TOKEN_MAX_AGE = 1209600; // 14일

    protected void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_TOKEN_MAX_AGE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    protected void addTempTokenCookie(HttpServletResponse response, String tempToken) {
        Cookie cookie = new Cookie("tempToken", tempToken);
        cookie.setPath("/");
        cookie.setMaxAge(TEMP_TOKEN_MAX_AGE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 쿠키 삭제
     */

}