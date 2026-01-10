package org.sopt.carena.member.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Member {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private LocalDateTime createdAt;
    private AuthType authType;
    private String authId;
    private Long score;

    public static Member create(String name, LocalDate birthdate, Gender gender, String authId, AuthType authType ) {
        return Member.builder()
                .name(name)
                .birthdate(birthdate)
                .gender(gender)
                .authId(authId)
                .authType(authType)
                .score(0L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
