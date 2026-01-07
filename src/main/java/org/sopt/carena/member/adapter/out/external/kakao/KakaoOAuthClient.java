package org.sopt.carena.member.adapter.out.external.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.out.external.kakao.dto.KakaoOAuthInfo;
import org.sopt.carena.member.adapter.out.external.kakao.dto.KakaoTokenResponse;
import org.sopt.carena.member.appliacation.port.out.KakaoOAuthPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements KakaoOAuthPort {

    private final RestTemplate restTemplate;
    private final KakaoOidcVerifier kakaoOidcVerifier;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Override
    public String getIdToken(String code) {
        log.info("카카오 토큰 발급 요청 시작");

        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                KakaoTokenResponse.class
        );

        KakaoTokenResponse tokenResponse = response.getBody();
        if (tokenResponse == null || tokenResponse.getIdToken() == null) {
            throw new IllegalStateException("카카오 토큰 발급 실패");
        }

        log.info("카카오 ID Token 발급 완료");
        return tokenResponse.getIdToken();
    }

    @Override
    public KakaoOAuthInfo verifyIdToken(String idToken) {
        return kakaoOidcVerifier.verify(idToken);
    }
}
