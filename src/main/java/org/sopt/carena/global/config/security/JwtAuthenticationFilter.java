package org.sopt.carena.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.FailureResponse;
import org.sopt.carena.global.config.security.util.PublicEndpoint;
import org.sopt.carena.global.config.security.util.AccessTokenResolver;
import org.sopt.carena.member.application.port.out.AccessTokenBlacklistStore;
import org.sopt.carena.member.application.service.util.JwtTokenParser;
import org.sopt.carena.member.application.service.util.JwtTokenValidator;
import org.sopt.carena.member.domain.Role;
import org.sopt.carena.member.exception.code.MemberErrorCode;
import org.sopt.carena.member.exception.jwt.EmptyTokenException;
import org.sopt.carena.member.exception.jwt.InvalidTokenException;
import org.sopt.carena.member.exception.jwt.MalformedTokenException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenParser jwtTokenParser;
    private final ObjectMapper objectMapper;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AccessTokenBlacklistStore accessTokenBlacklistStore;

    public JwtAuthenticationFilter(JwtTokenValidator jwtTokenValidator,
                                   JwtTokenParser jwtTokenParser, ObjectMapper objectMapper,
                                   @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver,
                                   AccessTokenBlacklistStore accessTokenBlacklistStore) {
        this.jwtTokenValidator = jwtTokenValidator;
        this.jwtTokenParser = jwtTokenParser;
        this.objectMapper = objectMapper;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.accessTokenBlacklistStore = accessTokenBlacklistStore;
    }

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
            String accessToken = AccessTokenResolver.resolve(request);

            if (accessTokenBlacklistStore.isBlacklisted(accessToken)) {
                log.warn("블랙리스트 처리된 액세스 토큰");
                handlerExceptionResolver.resolveException(
                        request,
                        response,
                        null,
                        new InvalidTokenException()
                );
                return;
            }

            if (!jwtTokenValidator.isValid(accessToken)) {
                log.warn("유효하지 않은 액세스 토큰");
                handlerExceptionResolver.resolveException(request, response, null, new InvalidTokenException());
                return;  // 필터 체인 중단
            }
            Long memberId = jwtTokenParser.getMemberId(accessToken);
            Role memberRole = jwtTokenParser.getRole(accessToken);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            memberId,
                            null,
                            List.of(new SimpleGrantedAuthority(memberRole.name()))
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("role- : {}", memberRole);
            log.debug("JWT 인증 성공 - MemberId: {}", memberId);


            filterChain.doFilter(request, response);
        } catch (EmptyTokenException | MalformedTokenException e) {
            log.warn("토큰 추출 실패: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
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

    private void sendErrorResponse(HttpServletResponse response, MemberErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        FailureResponse failureResponse = FailureResponse.of(errorCode);
        String jsonResponse = objectMapper.writeValueAsString(failureResponse);
        response.getWriter().write(jsonResponse);
    }
}
