package org.sopt.carena.member.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
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

    public MemberJpaEntity(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.birthdate = member.getBirthdate();
        this.gender = member.getGender();
        this.createdAt = member.getCreatedAt();
        this.authType = member.getAuthType();
        this.authId = member.getAuthId();
        this.score = member.getScore();
    }
}