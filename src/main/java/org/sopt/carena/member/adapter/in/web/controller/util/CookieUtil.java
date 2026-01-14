package org.sopt.carena.member.adapter.in.web.controller.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {
    private static final int TEMP_TOKEN_MAX_AGE = 600; // 10분
    private static final int REFRESH_TOKEN_MAX_AGE = 1209600; // 14일

    public static void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(REFRESH_TOKEN_MAX_AGE)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void addTempTokenCookie(HttpServletResponse response, String tempToken) {
        ResponseCookie cookie = ResponseCookie.from("tempToken", tempToken)
                .path("/")
                .maxAge(TEMP_TOKEN_MAX_AGE)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void deleteTempTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("tempToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void deleteRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken","")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}