package org.sopt.carena.member.adapter.out.persistence.repository;

import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.domain.AuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByAuthIdAndAuthType(String authId, AuthType authType);
    boolean existsByAuthIdAndAuthType(String authId, AuthType authType);
}