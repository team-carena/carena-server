package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberJpaEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByAuthIdAndAuthType(String authId, AuthType authType) {
        return memberJpaRepository
                .findByAuthIdAndAuthType(authId, authType)
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        MemberJpaEntity entity = MemberJpaEntity.from(member);
        MemberJpaEntity saved = memberJpaRepository.save(entity);
        return saved.toDomain();
    }
    @Override
    public boolean existsByAuthIdAndAuthType(String authId, AuthType authType) {
        return memberJpaRepository.existsByAuthIdAndAuthType(authId, authType);  // ← 추가!
    }
}