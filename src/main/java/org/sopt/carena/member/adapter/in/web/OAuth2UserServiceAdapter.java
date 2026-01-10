package org.sopt.carena.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 * Spring Security OAuth2와 애플리케이션을 연결하는 어댑터
 * Spring 프레임워크에만 의존
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceAdapter extends OidcUserService {

    private final OAuth2LoginUseCase oauth2LoginUseCase;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        log.info("OAuth2UserServiceAdapter 실행");
        OidcUser oidcUser = super.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase();
        AuthType authType = AuthType.valueOf(registrationId);
      return switch (authType) {
            case KAKAO -> handleKakaoOidc(oidcUser);
            default -> throw new OAuth2AuthenticationException(
                    "Unsupported OAuth provider: " + authType
            );
        };
    }
    private OidcUser handleKakaoOidc(OidcUser oidcUser) {

        log.info("🟡 handleKakaoOidc 진입");
        log.info("Kakao sub = {}", oidcUser.getSubject());
        log.info("Kakao email = {}", oidcUser.getEmail());

        // 표준 OIDC subject
        String sub = oidcUser.getSubject();

        // 카카오는 email이 null일 수도 있음
        String email = oidcUser.getEmail();

        OAuth2LoginCommand command = OAuth2LoginCommand.ofOidc(
                AuthType.KAKAO,
                sub,
                email
        );
        OAuth2LoginView result = oauth2LoginUseCase.processLogin(command);

        return new OAuth2AuthenticationResult(oidcUser, result);
    }
}