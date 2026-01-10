package org.sopt.carena.member.appliacation.dto.view;

/**
 * OAuth2 로그인 결과
 */
public record OAuth2LoginView(
        boolean needsSignup,
        String accessToken,
        String refreshToken,
        String tempToken,
        MemberView member
) {
    public static OAuth2LoginView forExistingMember(
            String accessToken,
            String refreshToken,
            MemberView member
    ) {
        return new OAuth2LoginView(
                false,
                accessToken,
                refreshToken,
                null,
                member);
    }

    public static OAuth2LoginView forNewMember(String tempToken) {
        return new OAuth2LoginView(
                true,
                null,
                null,
                tempToken,
                null);
    }
}