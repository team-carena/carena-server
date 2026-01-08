package org.sopt.carena.member.appliacation.dto.command;

import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.appliacation.exception.member.InvalidNameException;
import org.sopt.carena.member.domain.Gender;
import org.sopt.carena.member.appliacation.exception.member.InvalidBirthdateException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public record SignUpCommand(
        String tempToken,
        String name,
        String birthdate,
        Gender gender
) {
    public SignUpCommand {
        validate(tempToken, name, birthdate, gender);
    }

    private void validate(
            String tempToken,
            String name,
            String birthdate,
            Gender gender
    ) {
        // 1. 이름 정책
        if (name.length() > 20) {
            throw new InvalidNameException();
        }

        // 2. 생년월일 정책
        validateBirthdate(birthdate);
    }

    private void validateBirthdate(String birthdate) {
        LocalDate date;
        date = LocalDate.parse(birthdate);

        int year = date.getYear();
        if (year < 1960 || year > 2007) {
            throw new InvalidBirthdateException();
        }
    }

    public static SignUpCommand from(SignUpRequest request) {

        return new SignUpCommand(
                request.tempToken(),
                request.name(),
                request.birthdate(),
                request.gender()
        );
    }
}