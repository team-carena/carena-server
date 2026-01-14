package org.sopt.carena.member.application.dto.view;

public interface OAuth2LoginResult {
    default boolean needsSignup() {
        return this instanceof NewMemberSignupView;
    }
}
