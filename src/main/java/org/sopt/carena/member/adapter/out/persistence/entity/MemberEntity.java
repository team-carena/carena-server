package org.sopt.carena.member.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "members",indexes = {
    @Index(name = "idx_member_auth", columnList = "auth_type, auth_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

    @Id
    @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //name 추가하기
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

    @Builder
    private MemberEntity(Long id, String name, LocalDate birthdate, Gender gender,
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
}