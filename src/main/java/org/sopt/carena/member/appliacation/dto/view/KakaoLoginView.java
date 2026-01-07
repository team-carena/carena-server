package org.sopt.carena.member.appliacation.dto.view;

public record KakaoLoginView(
        boolean needsSignup,
        String tempToken,
        String accessToken,
        MemberView member
) {
    public static KakaoLoginView forNewMember(String tempToken) {
        return new KakaoLoginView(
                true,
                tempToken,
                null,
                null
        );
    }

    public static KakaoLoginView forExistingMember(String accessToken, MemberView member) {
        return new KakaoLoginView(
                false,
                null,
                accessToken,
                member
        );
    }
}