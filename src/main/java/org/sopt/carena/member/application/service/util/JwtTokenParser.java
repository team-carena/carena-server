package org.sopt.carena.member.application.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.domain.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenParser {

    private final JwtProperties jwtProperties;
    private static final String ROLE_CLAIM_KEY = "role";

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
    public Role getRole(String token) {
        Claims claims = parseClaims(token);
        String roleString = claims.get(ROLE_CLAIM_KEY, String.class);
        return Role.valueOf(roleString);
    }

    public long getRemainingValidityMillis(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(jwtProperties.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.getTime() - System.currentTimeMillis();
    }
}
