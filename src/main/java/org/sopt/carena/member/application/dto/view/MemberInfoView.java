package org.sopt.carena.member.application.dto.view;

import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;
import java.time.Period;

public record MemberInfoView(
        String name,
        int age,
        Gender gender,
        Long score
) {
    private MemberInfoView(
            String name,
            LocalDate birthdate,
            Gender gender,
            Long score
    ) {
        this(
                name,
                calculateAge(birthdate),
                gender,
                score
        );
    }

    public static MemberInfoView of(
            String name,
            LocalDate birthdate,
            Gender gender,
            Long score
    ) {
        return new MemberInfoView(name, birthdate, gender, score);
    }

    private static int calculateAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
