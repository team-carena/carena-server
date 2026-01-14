package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.mapper.MemberMapper;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPersistencePort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> getMemberById(final Long id) {
        return memberJpaRepository.findById(id).map(MemberMapper::toDomain);
    }

    @Override
    public Optional<Member> findByAuthIdAndAuthType(final String authId, final AuthType authType) {
        return Optional.empty();
    }

    @Override
    public Member save(final Member member) {
        MemberEntity entity = MemberMapper.toEntity(member);
        MemberEntity saved = memberJpaRepository.save(entity);
        return MemberMapper.toDomain(saved);
    }
    @Override
    public boolean existsByAuthIdAndAuthType(final String authId, final AuthType authType) {
        return memberJpaRepository.existsByAuthIdAndAuthType(authId, authType);
    }
    @Override
    public Optional<Member> findByAuthTypeAndProviderUserId(final AuthType authType, final String providerUserId) {
        return memberJpaRepository.findByAuthIdAndAuthType(providerUserId,authType).map(MemberMapper::toDomain);
    }
}