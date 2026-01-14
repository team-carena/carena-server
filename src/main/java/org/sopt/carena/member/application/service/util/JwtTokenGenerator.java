package org.sopt.carena.member.application.service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.domain.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

//  토큰 생성 책임
@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;
    private static final String ROLE_CLAIM_KEY = "role";


    public String createAccessToken(Long memberId, Role role) {
        return createToken(memberId, role,jwtProperties.getAccessTokenValidityInMilliseconds());
    }

    public String createRefreshToken(Long memberId,Role role) {
        return createToken(memberId, role,jwtProperties.getRefreshTokenValidityInMilliseconds());
    }

    private String createToken(Long memberId, Role role,long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(ROLE_CLAIM_KEY, role.name())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(jwtProperties.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}