package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.mapper.MemberMapper;
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
    public Optional<Member> getMemberById(Long id) {
        return memberJpaRepository.findById(id).map(MemberMapper::toDomain);
    }

    @Override
    public Optional<Member> findByAuthIdAndAuthType(String authId, AuthType authType) {
        return memberJpaRepository.findByAuthIdAndAuthType(authId, authType).map(MemberMapper::toDomain);
    }
    @Override
    public Member save(Member member) {
        MemberEntity entity = MemberMapper.toEntity(member);
        MemberEntity saved = memberJpaRepository.save(entity);
        return MemberMapper.toDomain(saved);
    }
    @Override
    public boolean existsByAuthIdAndAuthType(String authId, AuthType authType) {
        return memberJpaRepository.existsByAuthIdAndAuthType(authId, authType);
    }
    @Override
    public Optional<Member> findByAuthTypeAndProviderUserId(AuthType authType, String providerUserId) {
        return memberJpaRepository.findByAuthIdAndAuthType(providerUserId,authType).map(MemberMapper::toDomain);
    }
}