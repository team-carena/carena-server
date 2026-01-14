package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.view.TokenGeneratedView;

public interface RefreshTokenUseCase {
    TokenGeneratedView refreshAccessToken(String refreshToken);
}