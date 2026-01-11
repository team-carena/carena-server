/*
package org.sopt.carena.member.adapter.out.external.kakao.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.exception.oauth.JwksRetrievalException;
import org.sopt.carena.member.appliacation.exception.oauth.PublicKeyConversionException;
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

    */
/**
     * 카카오 공개키 조회
     *//*

    @Cacheable(value = "kakaoJwks", key = "#kid")
    public RSAPublicKey getPublicKey(String kid) {
        log.debug("카카오 JWKS 조회 - kid: {}", kid);

        Map<String, Object> jwks = fetchJwks();
        Map<String, Object> jwk = findKeyById(jwks, kid);


        return convertToRsaPublicKey(jwk);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchJwks() {
        try {
            log.debug("카카오 JWKS URL 호출: {}", KAKAO_JWKS_URL);

            Map<String, Object> jwks = restTemplate.getForObject(
                    URI.create(KAKAO_JWKS_URL),
                    Map.class
            );

            if (jwks == null) {
                throw new JwksRetrievalException("JWKS 응답이 null입니다");
            }

            return jwks;

        } catch (Exception e) {
            log.error("카카오 JWKS 조회 실패", e);
            throw new JwksRetrievalException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findKeyById(Map<String, Object> jwks, String kid) {
        List<Map<String, Object>> keys =
                (List<Map<String, Object>>) jwks.get("keys");

        if (keys == null || keys.isEmpty()) {
            log.error("JWKS에 키가 없습니다");
            throw new JwksRetrievalException("JWKS에 키가 없습니다");  
        }

        return keys.stream()
                .filter(key -> kid.equals(key.get("kid")))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("일치하는 카카오 공개키 없음 - kid: {}, 사용 가능한 키: {}",
                            kid,
                            keys.stream().map(k -> k.get("kid")).toList()
                    );
                    return new JwksRetrievalException(
                            "일치하는 공개키를 찾을 수 없습니다: " + kid);
                });

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
            throw new PublicKeyConversionException();
        }
    }
}*/
