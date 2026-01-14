package org.sopt.carena.member.application.dto.view;

import java.time.LocalDate;

public record MyPageInfoView(
        String name,
        LocalDate birthdate
) {
    public static MyPageInfoView of(final String name, final LocalDate birthdate) {
        return new MyPageInfoView(
                name, birthdate);
    }
}
