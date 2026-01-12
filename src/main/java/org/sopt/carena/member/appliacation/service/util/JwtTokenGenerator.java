package org.sopt.carena.member.appliacation.service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

//  토큰 생성 책임
@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;

    public String createAccessToken(Long memberId) {
        return createToken(memberId, jwtProperties.getAccessTokenValidityInMilliseconds());
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, jwtProperties.getRefreshTokenValidityInMilliseconds());
    }

    private String createToken(Long memberId, long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(jwtProperties.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}