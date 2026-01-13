package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.command.SignUpCommand;
import org.sopt.carena.member.application.dto.view.SignupView;

public interface SignupUseCase {
    SignupView signup(SignUpCommand command);
}

