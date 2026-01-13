package org.sopt.carena.member.appliacation.dto.view;

public interface OAuth2LoginResult {
    default boolean needsSignup() {
        return this instanceof NewMemberSignupView;
    }
}
