package org.sopt.carena.member.appliacation.dto.view;

public record NewMemberSignupView(
        String tempToken
) implements OAuth2LoginResult {
    public static NewMemberSignupView of(String tempToken) {
        return new NewMemberSignupView(tempToken);
    }
}