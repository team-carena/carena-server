package org.sopt.carena.member.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Gender;
import org.sopt.carena.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    @Column(nullable = false, unique = true)
    private String authId;

    @Column(nullable = false)
    private Long score;

    public Member toDomain() {
        return new Member(
                id, name, birthdate, gender,
                createdAt, authType, authId, score
        );
    }

    public static MemberJpaEntity from(Member member) {
        MemberJpaEntity entity = new MemberJpaEntity();
        entity.id = member.getId();
        entity.name = member.getName();
        entity.birthdate = member.getBirthdate();
        entity.gender = member.getGender();
        entity.createdAt = member.getCreatedAt();
        entity.authType = member.getAuthType();
        entity.authId = member.getAuthId();
        entity.score = member.getScore();
        return entity;
    }
}