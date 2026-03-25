package org.sopt.carena.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.member.exception.member.InvalidBirthdateException;
import org.sopt.carena.member.exception.member.InvalidNameException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

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
    private Role role;

    @Builder
    private Member(Long id, String name, LocalDate birthdate, Gender gender,
                   LocalDateTime createdAt, AuthType authType, String authId, Long score,Role role) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.authType = authType;
        this.authId = authId;
        this.score = score;
        this.role = role;
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
                .role(Role.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static void validate(String name, LocalDate birthdate) {
        if (name == null || name.length() > 20) {
            throw new InvalidNameException();
        }
        int year = birthdate.getYear();
        if (year < 1956 || year > 2007) {
            throw new InvalidBirthdateException();
        }
    }
    //만나이 계산
    public int getAge() {
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    public void updateScore(Long score) {
        this.score = score;
    }
}
