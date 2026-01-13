package org.sopt.carena.member.application.dto.view;

public record LoginSuccessView(
        String accessToken,
        String refreshToken,
        MemberView member
) implements OAuth2LoginResult {
    public static LoginSuccessView of(
            final String accessToken,
            final String refreshToken,
            final MemberView member
    ) {
        return new LoginSuccessView(accessToken, refreshToken, member);
    }
}