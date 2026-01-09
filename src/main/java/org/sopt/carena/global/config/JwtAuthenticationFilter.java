package org.sopt.carena.global.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.service.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    // JWT 필터를 건너뛸 경로들
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/api-docs",
            "/webjars",
            "/api/v1/member/token/refresh"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // EXCLUDE_URLS의 경로로 시작하면 필터 건너뛰기
        boolean shouldExclude = EXCLUDE_URLS.stream()
                .anyMatch(path::startsWith);

        if (shouldExclude) {
            log.debug("JWT 필터 제외 경로 - URI: {}", path);
        }

        return shouldExclude;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.debug("JWT 필터 실행 - URI: {}", requestURI);

        // JWT 검증 건너뛰기
        if (shouldNotFilter(request)) {
            log.debug("JWT 필터 건너뛰기 - URI: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. 쿠키에서 JWT 추출
            String jwt = getJwtFromCookie(request);

            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                // 2. JWT에서 memberId 추출
                Long memberId = jwtTokenProvider.getMemberIdFromToken(jwt);

                // 3. Spring Security Context에 저장
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                null
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("JWT 인증 성공 - memberId: {}", memberId);
            }
        } catch (Exception e) {
            log.error("JWT 인증 실패", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
