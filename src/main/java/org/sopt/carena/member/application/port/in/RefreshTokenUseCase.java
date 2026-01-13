package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.view.TokenRefreshView;

public interface RefreshTokenUseCase {
    TokenRefreshView refreshAccessToken(String refreshToken);
}