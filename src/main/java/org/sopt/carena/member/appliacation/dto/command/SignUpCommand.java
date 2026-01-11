package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.appliacation.exception.member.InvalidNameException;
import org.sopt.carena.member.domain.Gender;
import org.sopt.carena.member.appliacation.exception.member.InvalidBirthdateException;

import java.time.LocalDate;

public record SignUpCommand(
        String tempToken,
        String name,
        LocalDate birthdate,
        Gender gender
) {
    public SignUpCommand {
        validate(tempToken, name, birthdate, gender);
    }

    private void validate(
            String tempToken,
            String name,
            LocalDate birthdate,
            Gender gender
    ) {
        // 이름 정책
        if (name.length() > 20) {
            throw new InvalidNameException();
        }
        // 생년월일 정책
        validateBirthdate(birthdate);
    }

    private void validateBirthdate(LocalDate localDate) {
        int year = localDate.getYear();
        if (year < 1960 || year > 2007) {
            throw new InvalidBirthdateException();
        }
    }
    public static SignUpCommand of(String tempToken, SignUpRequest request) {
        return new SignUpCommand(
                tempToken,
                request.name(),
                request.birthdate(),
                request.gender()
        );
    }
}