package org.sopt.carena.member.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.member.exception.member.InvalidNameOrBirthdateException;

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
        if (!validate(name, birthdate)) {
            throw new InvalidNameOrBirthdateException();
        }
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

    private static boolean validate(String name, LocalDate birthdate) {
        if (name.length() > 20) {
            return false;
        }
        int year = birthdate.getYear();
        if (year <1960 || year > 2007 ) {
            return false;
        }
        return true;
    }
}
