package org.sopt.carena.member.adapter.out.persistence.mapper;

import org.sopt.carena.member.adapter.out.persistence.entity.MemberJpaEntity;
import org.sopt.carena.member.domain.Member;

public class MemberMapper {
    public static Member toDomain(MemberJpaEntity memberJpaEntity) {
        return Member.builder()
                .id(memberJpaEntity.getId())
                .name(memberJpaEntity.getName())
                .birthdate(memberJpaEntity.getBirthdate())
                .gender(memberJpaEntity.getGender())
                .createdAt(memberJpaEntity.getCreatedAt())
                .authType(memberJpaEntity.getAuthType())
                .authId(memberJpaEntity.getAuthId())
                .score(memberJpaEntity.getScore())
                .build();
    }

    public static MemberJpaEntity toEntity(Member member) {
        return MemberJpaEntity.builder()
                .name(member.getName())
                .birthdate(member.getBirthdate())
                .gender(member.getGender())
                .createdAt(member.getCreatedAt())
                .authType(member.getAuthType())
                .authId(member.getAuthId())
                .score(member.getScore())
                .build();
    }


}
