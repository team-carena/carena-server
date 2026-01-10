/*
package org.sopt.carena.member.appliacation.dto.view;

public record KakaoLoginView(
        boolean needsSignup,
        String tempToken,
        String accessToken,
        String refreshToken,
        MemberView member
) {
    public static KakaoLoginView forNewMember(String tempToken) {
        return new KakaoLoginView(
                true,
                tempToken,
                null,
                null,
                null
        );
    }

    public static KakaoLoginView forExistingMember(String accessToken,String refreshToken, MemberView member) {
        return new KakaoLoginView(
                false,
                null,
                accessToken,
                refreshToken,
                member
        );
    }
}*/
