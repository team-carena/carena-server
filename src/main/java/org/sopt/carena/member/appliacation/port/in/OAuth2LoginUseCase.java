package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;

/**
 * OAuth2 로그인 유스케이스
 */
public interface OAuth2LoginUseCase {
    /**
     * OAuth2 사용자 정보로 로그인 처리
     *
     * @param command OAuth2 사용자 정보
     * @return 로그인 결과 (신규/기존 회원 정보)
     */
    OAuth2LoginView processLogin(OAuth2LoginCommand command);
}