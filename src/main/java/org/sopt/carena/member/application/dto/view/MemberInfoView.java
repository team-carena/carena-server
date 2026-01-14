package org.sopt.carena.member.application.dto.view;

import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;

public record MemberInfoView(
        String name,
        int age,
        Gender gender,
        Long score
) {
    public static MemberInfoView of(
            final String name,
            final int age,
            final Gender gender,
            final Long score
    ) {
        return new MemberInfoView(name,age ,gender, score);
    }
}
