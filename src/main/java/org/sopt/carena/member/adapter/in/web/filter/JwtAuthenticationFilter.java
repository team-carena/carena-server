package org.sopt.carena.member.adapter.in.web.filter;


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
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*
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

     */

    /**
     * Authorization 헤더에서 JWT 추출
     * "Bearer {token}"
     */
    /*
    private String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

     */

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.debug("JWT 필터 실행 - URI: {}", uri);

        // OAuth2 로그인 경로는 건너뛰기 (중요!)
        if (shouldSkipFilter(uri)) {
            log.debug("JWT 필터 건너뛰기 - URI: {}", uri);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1순위: 쿠키에서 토큰 추출 (OAuth2 로그인 후)
            String token = extractTokenFromCookie(request);

            // 2순위: Authorization 헤더에서 추출 (API 호출 시)
            if (token == null) {
                token = extractTokenFromHeader(request);
            }

            // JWT 검증 및 인증 설정
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Long memberId = jwtTokenProvider.getMemberIdFromToken(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                Collections.emptyList()  // 권한 목록
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("✅ JWT 인증 성공 - MemberId: {}", memberId);
            }
        } catch (Exception e) {
            log.error("JWT 인증 실패: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * JWT 필터를 건너뛸 경로 확인
     */
    private boolean shouldSkipFilter(String uri) {
        return uri.startsWith("/oauth2/")              // OAuth2 인증 시작
                || uri.startsWith("/login/oauth2/")        // OAuth2 콜백
                || uri.equals("/login")                    // 로그인 페이지
                || uri.startsWith("/swagger-ui")           // Swagger UI
                || uri.startsWith("/v3/api-docs")          // API 문서
                || uri.startsWith("/api-docs")             // API 문서
                || uri.equals("/api/v1/member/signup")     // 회원가입
                || uri.equals("/api/v1/member/token/refresh");  // 토큰 갱신
    }

    /**
     * 쿠키에서 Access Token 추출
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
