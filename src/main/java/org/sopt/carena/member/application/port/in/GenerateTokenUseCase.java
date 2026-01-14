package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.view.TokenGeneratedView;

public interface GenerateTokenUseCase {
    TokenGeneratedView generateToken(String oneTimeToken);
}
