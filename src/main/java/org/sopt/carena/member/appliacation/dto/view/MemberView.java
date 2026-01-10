package org.sopt.carena.member.appliacation.dto.view;

import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Gender;
import org.sopt.carena.member.domain.Member;

import java.time.LocalDate;


public record MemberView(
        Long id,
        String name,
        AuthType authType,
        LocalDate birthdate,
        Gender gender,
        Long score
) {
    public static MemberView from(Member member) {
        return new MemberView(
                member.getId(),
                member.getName(),
                member.getAuthType(),
                member.getBirthdate(),
                member.getGender(),
                member.getScore()
        );
    }
}