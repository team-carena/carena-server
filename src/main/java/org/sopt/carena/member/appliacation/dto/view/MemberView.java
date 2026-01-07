package org.sopt.carena.member.appliacation.dto.view;

import org.sopt.carena.member.domain.Gender;
import org.sopt.carena.member.domain.Member;


public record MemberView(
        Long id,
        String name,
        String birthdate,
        Gender gender,
        Long score
) {
    public static MemberView from(Member member) {
        return new MemberView(
                member.getId(),
                member.getName(),
                member.getBirthdate(),
                member.getGender(),
                member.getScore()
        );
    }
}