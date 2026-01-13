package org.sopt.carena.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.member.exception.member.InvalidBirthdateException;
import org.sopt.carena.member.exception.member.InvalidNameException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private LocalDateTime createdAt;
    private AuthType authType;
    private String authId;
    private Long score;

    @Builder
    private Member(Long id, String name, LocalDate birthdate, Gender gender,
                   LocalDateTime createdAt, AuthType authType, String authId, Long score) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.authType = authType;
        this.authId = authId;
        this.score = score;
    }

    public static Member create(String name, LocalDate birthdate, Gender gender, String authId, AuthType authType ) {
        validate(name, birthdate);

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

    private static void validate(String name, LocalDate birthdate) {
        if (name == null || name.length() > 20) {
            throw new InvalidNameException();
        }
        int year = birthdate.getYear();
        if (year < 1960 || year > 2007) {
            throw new InvalidBirthdateException();
        }
    }
}
