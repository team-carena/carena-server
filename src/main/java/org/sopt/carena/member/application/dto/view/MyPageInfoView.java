package org.sopt.carena.member.application.dto.view;

import java.time.LocalDate;

public record MyPageInfoView(
        String name,
        LocalDate birthdate
) {
    public static MyPageInfoView of(String name, LocalDate birthdate) {
        return new MyPageInfoView(
                name, birthdate);
    }
}
