package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.command.OAuth2LoginCommand;
import org.sopt.carena.member.appliacation.dto.view.OAuth2LoginView;

public interface OAuth2LoginUseCase {
    OAuth2LoginView processLogin(OAuth2LoginCommand command);
}