package org.sopt.carena.member.application.dto.command;

import org.sopt.carena.member.adapter.in.web.dto.request.SignUpRequest;
import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;

public record SignUpCommand(
        String tempToken,
        String name,
        LocalDate birthdate,
        Gender gender
) {
    public static SignUpCommand of(final String tempToken, final SignUpRequest request) {
        return new SignUpCommand(
                tempToken,
                request.name(),
                request.birthdate(),
                request.gender()
        );
    }
}