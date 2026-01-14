package org.sopt.carena.global.config.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.carena.member.exception.jwt.EmptyTokenException;
import org.sopt.carena.member.exception.jwt.MalformedTokenException;

// 헤더에서 엑세스 토큰을 추출하는 유틸
public class AccessTokenResolver {

    public static String resolve(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new EmptyTokenException();
        }

        if (!header.startsWith("Bearer ")) {
            throw new MalformedTokenException();
        }
        return header.substring(7);
    }
}
