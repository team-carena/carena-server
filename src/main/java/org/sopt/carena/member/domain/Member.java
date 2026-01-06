package org.sopt.carena.member.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String birthdate;
    private Gender gender;
    private LocalDateTime createdAt;
    private AuthType authType;
    private String authId;
    private Long score;

    public static Member create(String name, String birthdate, Gender gender, String authId) {
        return new Member(
                null,
                name,
                birthdate,
                gender,
                LocalDateTime.now(),
                AuthType.KAKAO,
                authId,
                0L
        );
    }
}
