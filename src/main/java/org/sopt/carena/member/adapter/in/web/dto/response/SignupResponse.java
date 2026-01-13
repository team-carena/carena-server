package org.sopt.carena.member.adapter.in.web.dto.response;

import org.sopt.carena.member.appliacation.dto.view.SignupView;

public record SignupResponse(
        Long memberId
) {
    public static SignupResponse from(SignupView signupView) {
        return new SignupResponse(
                signupView.member().id()
        );
    }
}