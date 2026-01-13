package org.sopt.carena.member.adapter.out.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.in.web.dto.OAuth2AuthenticationResult;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.sopt.carena.member.exception.oauth.UnsupportedOAuthProviderException;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import static org.sopt.carena.member.domain.AuthType.KAKAO;

/**
 * Spring Security OAuth2와 애플리케이션을 연결하는 어댑터
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceAdapter extends OidcUserService {

    private final OAuth2LoginUseCase oauth2LoginUseCase;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("=== OIDC 사용자 정보 로드 시작 ===");
        OidcUser oidcUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase();

        AuthType authType;
        try {
            authType = AuthType.valueOf(registrationId);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOAuthProviderException();
        }

        OAuth2LoginCommand command = switch (authType) {
            case KAKAO -> extractKakaoInfo(oidcUser);
        };

        OAuth2LoginView loginResult = oauth2LoginUseCase.processLogin(command);
        // Spring Security 인증 객체로 래핑하여 반환
        return new OAuth2AuthenticationResult(oidcUser, loginResult);
    }
    private OAuth2LoginCommand extractKakaoInfo(OidcUser oidcUser) {
        // 표준 OIDC subject
        String providerUserId = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        return OAuth2LoginCommand.of(KAKAO, providerUserId, email);
    }
}