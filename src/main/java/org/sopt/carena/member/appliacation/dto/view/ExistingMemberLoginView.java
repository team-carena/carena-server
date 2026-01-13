package org.sopt.carena.member.appliacation.dto.view;

public record ExistingMemberLoginView(
        String accessToken,
        String refreshToken,
        MemberView member
) implements OAuth2LoginResult {
    public static ExistingMemberLoginView of(
            String accessToken,
            String refreshToken,
            MemberView member
    ) {
        return new ExistingMemberLoginView(accessToken, refreshToken, member);
    }
}