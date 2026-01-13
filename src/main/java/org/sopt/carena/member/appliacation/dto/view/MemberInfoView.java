package org.sopt.carena.member.appliacation.dto.view;

import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;

public record MemberInfoView(
        Long id,
        String name,
        LocalDate birthdate,
        Gender gender,
        Long score
) {
    public static MemberInfoView of(
            Long id,
            String name,
            LocalDate birthdate,
            Gender gender,
            Long score
    ) {
        return new MemberInfoView(id, name, birthdate,gender, score);
    }
}
