package org.sopt.carena.member.adapter.out.persistence.repository;

import org.sopt.carena.member.adapter.out.persistence.entity.MemberJpaEntity;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findByAuthIdAndAuthType(String authId, AuthType authType);
}