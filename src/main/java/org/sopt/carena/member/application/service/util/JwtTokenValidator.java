package org.sopt.carena.member.application.service.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.exception.jwt.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final JwtTokenParser jwtTokenParser;

    public void validateToken(final String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new EmptyTokenException();
        }
        try {
            jwtTokenParser.parseClaims(token);
        } catch (ExpiredJwtException e) {throw new ExpiredTokenException();

        } catch (SignatureException e) {
            throw new TokenSignatureException();
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedTokenException();
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    /**
     * boolean 반환 - Filter에서 사용
     */
    public boolean isValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (RuntimeException e) {
            log.debug("토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }
}