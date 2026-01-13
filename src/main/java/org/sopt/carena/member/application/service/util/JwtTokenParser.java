package org.sopt.carena.member.application.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenParser {

    private final JwtProperties jwtProperties;

    public Long getMemberId(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtProperties.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
