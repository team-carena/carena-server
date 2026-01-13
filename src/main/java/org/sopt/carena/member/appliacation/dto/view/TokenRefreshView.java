package org.sopt.carena.member.appliacation.dto.view;

public record TokenRefreshView(
        String accessToken,
        String refreshToken
) {}