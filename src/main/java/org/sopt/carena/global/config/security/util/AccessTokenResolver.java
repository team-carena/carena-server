package org.sopt.carena.global.config.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.carena.member.exception.jwt.EmptyTokenException;
import org.springframework.stereotype.Component;

// 헤더에서 엑세스 토큰을 추출하는 유ㅇ틸
public class AccessTokenResolver {

    public static String resolve(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new EmptyTokenException();
        }

        return header.substring(7);
    }
}
