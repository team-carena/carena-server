package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.exception.UnsupportedOAuthProviderException;
import org.sopt.carena.member.appliacation.port.in.OAuthLoginUseCase;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Override
    public String getAuthUrl(String provider) {
        log.info("OAuth URL 생성 요청 - provider: {}", provider);
        validateProvider(provider);

        return switch (provider.toLowerCase()) {
            case "kakao" -> generateKakaoAuthUrl();
            default -> throw new UnsupportedOAuthProviderException();
        };
    }

    private String generateKakaoAuthUrl() {
        return String.format(
                "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=openid",
                kakaoClientId,
                kakaoRedirectUri
        );
    }

    private void validateProvider(String provider) {
        if (!AuthType.isSupported(provider)) {
            throw new UnsupportedOAuthProviderException();
        }
    }
}