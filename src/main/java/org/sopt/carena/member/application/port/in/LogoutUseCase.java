package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.command.SignUpCommand;

public interface LogoutUseCase {
    void logout(Long memberId);
}
