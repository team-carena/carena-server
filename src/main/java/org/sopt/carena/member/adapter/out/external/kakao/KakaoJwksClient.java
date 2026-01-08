package org.sopt.carena.member.adapter.out.external.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoJwksClient {

    private final RestTemplate restTemplate;

    private static final String KAKAO_JWKS_URL =
            "https://kauth.kakao.com/.well-known/jwks.json";

    /**
     * 카카오 공개키 조회
     */
    @Cacheable(value = "kakaoJwks", key = "#kid")
    public RSAPublicKey getPublicKey(String kid) {
        log.debug("카카오 JWKS 조회 - kid: {}", kid);

        Map<String, Object> jwks = fetchJwks();
        Map<String, Object> jwk = findKeyById(jwks, kid);


        return convertToRsaPublicKey(jwk);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchJwks() {
        return restTemplate.getForObject(
                URI.create(KAKAO_JWKS_URL),
                Map.class
        );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findKeyById(Map<String, Object> jwks, String kid) {
        List<Map<String, Object>> keys =
                (List<Map<String, Object>>) jwks.get("keys");

        return keys.stream()
                .filter(key -> kid.equals(key.get("kid")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "일치하는 카카오 공개키를 찾을 수 없습니다: " + kid));
    }

    private RSAPublicKey convertToRsaPublicKey(Map<String, Object> jwk) {
        try {
            String n = (String) jwk.get("n");
            String e = (String) jwk.get("e");

            byte[] nBytes = Base64.getUrlDecoder().decode(n);
            byte[] eBytes = Base64.getUrlDecoder().decode(e);

            BigInteger modulus = new BigInteger(1, nBytes);
            BigInteger exponent = new BigInteger(1, eBytes);

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) keyFactory.generatePublic(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException("RSA 공개키 변환 실패", e);
        }
    }
}