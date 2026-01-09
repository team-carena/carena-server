package org.sopt.carena.global.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
            // Authorization 헤더에서 JWT 추출
            String jwt = getJwtFromHeader(request);

            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                Long memberId = jwtTokenProvider.getMemberIdFromToken(jwt);

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

    /**
     * Authorization 헤더에서 JWT 추출
     * "Bearer {token}"
     */
    private String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
