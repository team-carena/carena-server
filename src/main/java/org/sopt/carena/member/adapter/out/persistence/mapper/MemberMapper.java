package org.sopt.carena.member.adapter.out.persistence.mapper;

import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.domain.Member;

public class MemberMapper {
    public static Member toDomain(final MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .birthdate(memberEntity.getBirthdate())
                .gender(memberEntity.getGender())
                .createdAt(memberEntity.getCreatedAt())
                .authType(memberEntity.getAuthType())
                .authId(memberEntity.getAuthId())
                .score(memberEntity.getScore())
                .role(memberEntity.getRole())
                .build();
    }

    public static MemberEntity toEntity(final Member member) {
        return MemberEntity.builder()
                .name(member.getName())
                .birthdate(member.getBirthdate())
                .gender(member.getGender())
                .authType(member.getAuthType())
                .authId(member.getAuthId())
                .score(member.getScore())
                .role(member.getRole())
                .build();
    }
}
