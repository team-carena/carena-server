package org.sopt.carena.member.adapter.out.external.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.out.external.kakao.dto.KakaoOAuthInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOidcVerifier {

    @Value("${oauth.kakao.issuer}")
    private String issuer;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    public KakaoOAuthInfo verify(String idToken) {
        try {
            log.info("ID Token 검증 시작");

            // ID Token 디코딩
            DecodedJWT jwt = JWT.decode(idToken);

            // Issuer 검증
            String tokenIssuer = jwt.getIssuer();
            if (!issuer.equals(tokenIssuer)) {
                throw new IllegalArgumentException("Invalid issuer");
            }

            // Audience 검증 (client_id)
            String audience = jwt.getAudience().get(0);
            if (!clientId.equals(audience)) {
                throw new IllegalArgumentException("Invalid audience");
            }

            // Claims 추출
            String sub = jwt.getClaim("sub").asString();
            String email = jwt.getClaim("email").asString();

            log.info("ID Token 검증 완료 - sub: {}", sub);

            return new KakaoOAuthInfo(sub, email);

        } catch (Exception e) {
            log.error("ID Token 검증 실패", e);
            throw new IllegalArgumentException("유효하지 않은 ID Token", e);
        }
    }
}