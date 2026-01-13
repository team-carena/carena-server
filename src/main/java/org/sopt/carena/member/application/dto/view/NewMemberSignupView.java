package org.sopt.carena.member.application.dto.view;

public record NewMemberSignupView(
        String tempToken
) implements OAuth2LoginResult {
    public static NewMemberSignupView of(final String tempToken) {
        return new NewMemberSignupView(tempToken);
    }
}