package org.sopt.carena.member.application.dto.view;

public record TokenRefreshView(
        String accessToken,
        String refreshToken
) {}