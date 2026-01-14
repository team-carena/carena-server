package org.sopt.carena.member.application.dto.view;

public record TokenGeneratedView (
        String accessToken,
        String refreshToken
) {}
