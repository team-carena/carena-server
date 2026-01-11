package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;

public interface RefreshTokenUseCase {
    TokenRefreshView refreshAccessToken(String refreshToken);
}