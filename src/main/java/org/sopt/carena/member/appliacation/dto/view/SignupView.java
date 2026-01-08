package org.sopt.carena.member.appliacation.dto.view;

public record SignupView(
        String accessToken,
        String refreshToken,
        MemberView member
) {
}