package org.sopt.carena.member.application.dto.view;

public record LoginSuccessView(
        String oneTimeToken,
        MemberView member
) implements OAuth2LoginResult {
    public static LoginSuccessView of(
            final String oneTimeToken,
            final MemberView member
    ) {
        return new LoginSuccessView(oneTimeToken, member);
    }
}