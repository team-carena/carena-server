package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;

public interface RefreshTokenUseCase {

    /**
     * Access Token 재발급
     */
    TokenRefreshView refreshAccessToken(String refreshToken);
}