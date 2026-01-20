package org.sopt.carena.member.application.dto.view;

import org.sopt.carena.member.domain.Gender;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

public record MemberInfoView(
        String name,
        int age,
        Gender gender,
        Long score,
        LocalDate latestHealthCheckDate
) {
    private MemberInfoView(
            String name,
            LocalDate birthdate,
            Gender gender,
            Long score,
            LocalDate latestHealthCheckDate
    ) {
        this(
                name,
                calculateAge(birthdate),
                gender,
                score,
                latestHealthCheckDate

        );
    }

    public static MemberInfoView of(
            String name,
            LocalDate birthdate,
            Gender gender,
            Long score,
            LocalDate latestHealthCheckDate
    ) {
        return new MemberInfoView(name, birthdate, gender, score, latestHealthCheckDate);
    }

    private static int calculateAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
