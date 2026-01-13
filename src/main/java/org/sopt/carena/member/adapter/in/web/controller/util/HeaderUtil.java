package org.sopt.carena.member.adapter.in.web.controller.util;

import jakarta.servlet.http.HttpServletResponse;

public class HeaderUtil {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public static void setAuthorizationHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
    }
}
