package org.sopt.carena.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;
import org.sopt.carena.member.appliacation.port.in.OAuth2LoginUseCase;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Spring Security OAuth2와 애플리케이션을 연결하는 어댑터
 * Spring 프레임워크에만 의존
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceAdapter extends DefaultOAuth2UserService {

    private final OAuth2LoginUseCase oauth2LoginUseCase;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        log.info("OAuth2UserServiceAdapter 실행");

        // 1. Spring이 OAuth2 제공자에서 사용자 정보 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 제공자 타입 추출
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase();
        AuthType authType = AuthType.valueOf(registrationId);

        // 3. 애플리케이션 계층으로 위임
        OAuth2LoginCommand command = OAuth2LoginCommand.of(
                authType,
                oAuth2User.getAttributes()
        );

        OAuth2LoginView result = oauth2LoginUseCase.processLogin(command);

        // 4. Spring Security용 객체로 변환
        return new OAuth2AuthenticationResult(result, oAuth2User.getAttributes());
    }
}