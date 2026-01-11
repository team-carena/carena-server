package org.sopt.carena.member.adapter.in.web.dto;

import org.sopt.carena.member.appliacation.dto.view.SignupView;

public record SignupResponse(
        //String accessToken,
        Long memberId
) {
    public static SignupResponse from(SignupView signupView) {
        return new SignupResponse(
                //signupView.accessToken(),
                signupView.member().id()
        );
    }
}