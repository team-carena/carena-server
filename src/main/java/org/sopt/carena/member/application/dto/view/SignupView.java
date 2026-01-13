package org.sopt.carena.member.application.dto.view;

public record SignupView(
        String accessToken,
        String refreshToken,
        MemberView member
) {
}