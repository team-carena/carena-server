package org.sopt.carena.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.FailureResponse;
import org.sopt.carena.global.config.security.util.PublicEndpoint;
import org.sopt.carena.member.appliacation.service.util.JwtTokenParser;
import org.sopt.carena.member.appliacation.service.util.JwtTokenValidator;
import org.sopt.carena.member.exception.code.MemberErrorCode;
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
    private final ObjectMapper objectMapper;

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
        String accessToken = extractTokenFromHeader(request);

        if (accessToken == null || accessToken.isEmpty()) {
            log.debug("인증이 필요한 경로에 토큰이 없습니다 - URI: {}", uri);
            sendErrorResponse(response, MemberErrorCode.EMPTY_TOKEN);
            return;
        }

        if (jwtTokenValidator.isValid(accessToken)) {
            // 인증 설정
            Long memberId = jwtTokenParser.getMemberId(accessToken);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(memberId, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("JWT 인증 성공 - MemberId: {}", memberId);

        } else {
            // 유효하지 않은 토큰 → 401 응답
            log.warn("유효하지 않은 액세스 토큰");
            sendErrorResponse(response, MemberErrorCode.INVALID_TOKEN);
            return;  // 필터 체인 중단
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

    private String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, MemberErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        FailureResponse failureResponse = FailureResponse.of(errorCode);
        String jsonResponse = objectMapper.writeValueAsString(failureResponse);
        response.getWriter().write(jsonResponse);
    }
}
