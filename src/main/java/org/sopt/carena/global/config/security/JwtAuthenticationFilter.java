package org.sopt.carena.global.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.config.security.util.PublicEndpoint;
import org.sopt.carena.member.appliacation.service.util.JwtTokenParser;
import org.sopt.carena.member.appliacation.service.util.JwtTokenValidator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenParser jwtTokenParser;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        log.debug("JWT 필터 실행 - URI: {}", uri);

        // OAuth2 로그인 경로는 건너뛰기
        if (PublicEndpoint.isPublicEndpoint(uri, method)) {
            log.debug("JWT 필터 건너뛰기 - URI: {}", uri);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 쿠키에서 토큰 추출 (OAuth2 로그인 후)
            String token = extractTokenFromCookie(request);

            // Authorization 헤더에서 추출 (API 호출 시)
            if (token == null) {
                token = extractTokenFromHeader(request);
            }

            // JWT 검증 및 인증 설정
            if (jwtTokenValidator.validateToken(token)) {
                Long memberId = jwtTokenParser.getMemberId(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                Collections.emptyList()  // 권한 목록
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("JWT 인증 성공 - MemberId: {}", memberId);
            }
        } catch (Exception e) {
            log.error("JWT 인증 실패: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }


    /**
     * 쿠키에서 추출
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                log.debug("쿠키에서 토큰 추출 성공");
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Authorization 헤더에서 JWT 추출
     * "Bearer {token}" 형식
     */
    private String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.debug("Authorization 헤더에서 토큰 추출 성공");
            return bearerToken.substring(7);
        }
        return null;
    }
}
