package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.SignupView;

public interface SignupUseCase {
    SignupView signup(SignUpCommand command);
}

