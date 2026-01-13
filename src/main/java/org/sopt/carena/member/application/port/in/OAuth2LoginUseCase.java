package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.application.dto.view.OAuth2LoginResult;

public interface OAuth2LoginUseCase {
    OAuth2LoginResult processLogin(OAuth2LoginCommand command);
}