package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.domain.Gender;

public record SignUpCommand(
        String tempToken,
        String name,
        String birthdate,
        Gender gender
) {
    public static SignUpCommand from(SignUpRequest request) {

        return new SignUpCommand(
                request.tempToken(),
                request.name(),
                request.birthdate(),
                request.gender()
        );
    }
}