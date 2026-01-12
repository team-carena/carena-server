package org.sopt.carena.member.adapter.in.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.debug("JWT 필터 실행 - URI: {}", uri);

        // OAuth2 로그인 경로는 건너뛰기
        if (shouldSkipFilter(uri)) {
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
            if (token != null && jwtTokenValidator.validateToken(token)) {
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
     * JWT 필터를 건너뛸 경로 확인
     */
    private boolean shouldSkipFilter(String uri) {
        return uri.startsWith("/oauth2/")              // OAuth2 인증 시작
                || uri.startsWith("/login/oauth2/")        // OAuth2 콜백
                || uri.equals("/login")                    // 로그인 페이지
                || uri.startsWith("/swagger-ui")           // Swagger UI
                || uri.startsWith("/v3/api-docs")          // API 문서
                || uri.startsWith("/swagger-resources")    // Swagger 리소스
                || uri.startsWith("/webjars")              // WebJars
                || uri.equals("/")                         // 메인 페이지
                || uri.equals("/index.html")               // 메인 페이지
                || uri.equals("/signup.html")              // 회원가입 페이지
                || uri.equals("/favicon.ico")              // 파비콘
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
