package org.sopt.carena.member.appliacation.dto.view;

import org.sopt.carena.member.domain.Member;

import java.time.LocalDate;

public record MyPageInfoView(
        String name,
        LocalDate birthdate
) {
    public static MyPageInfoView of(Member member) {
        return new MyPageInfoView(member.getName(), member.getBirthdate());

    }


}
