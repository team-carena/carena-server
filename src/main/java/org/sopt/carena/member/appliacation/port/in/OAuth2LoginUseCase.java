package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginResult;

public interface OAuth2LoginUseCase {
    OAuth2LoginResult processLogin(OAuth2LoginCommand command);
}