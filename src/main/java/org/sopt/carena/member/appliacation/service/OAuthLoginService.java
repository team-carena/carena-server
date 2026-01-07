package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.port.in.OAuthLoginUseCase;
import org.sopt.carena.member.appliacation.port.out.OAuthUrlProviderPort;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {

    private final OAuthUrlProviderPort oAuthUrlProvider;

    @Override
    public String getAuthUrl(String provider) {
        log.info("OAuth URL 생성 요청 - provider: {}", provider);
        AuthType authType = AuthType.from(provider);
        return oAuthUrlProvider.getAuthorizationUrl(authType);
    }
}