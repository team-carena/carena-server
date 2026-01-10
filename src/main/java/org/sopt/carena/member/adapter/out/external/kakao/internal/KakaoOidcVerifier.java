/*
package org.sopt.carena.member.adapter.out.external.kakao.internal;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.out.external.kakao.dto.KakaoOAuthInfo;
import org.sopt.carena.member.appliacation.exception.oauth.InvalidIdTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.security.interfaces.RSAPublicKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOidcVerifier {

    private final KakaoJwksClient kakaoJwksClient;

    @Value("${oauth.kakao.issuer}")
    private String issuer;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    */
/**
     * 카카오 ID Token 검증
     *//*

    public KakaoOAuthInfo verify(String idToken) {
        try {
            // 1. kid 추출 (검증 없이)
            String kid = extractKid(idToken);

            // 2. 카카오 공개키 조회
            RSAPublicKey publicKey =  kakaoJwksClient.getPublicKey(kid);

            // 3. ID Token 검증
            DecodedJWT verifiedJwt = verifyToken(idToken, publicKey);

            // 4. 사용자 정보 추출
            return extractUserInfo(verifiedJwt);

        } catch (Exception e) {
            log.error("카카오 ID Token 검증 실패", e);
            throw new IllegalArgumentException("유효하지 않은 ID Token", e);
        }
    }

    private String extractKid(String idToken) {
        DecodedJWT decoded = JWT.decode(idToken);
        String kid = decoded.getKeyId();

        if (kid == null) {
            log.error("ID Token에 kid가 없습니다");
            throw new InvalidIdTokenException("ID Token에 kid가 없습니다");
        }

        return kid;
    }

    private DecodedJWT verifyToken(String idToken, RSAPublicKey publicKey) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(clientId)
                .acceptLeeway(60)
                .build();

        return verifier.verify(idToken);
    }

    private KakaoOAuthInfo extractUserInfo(DecodedJWT jwt) {
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email").asString();

        log.info("카카오 사용자 정보 추출 완료 - sub: {}", sub);

        return new KakaoOAuthInfo(sub, email);
    }
}
*/
