package org.sopt.carena.member.application.port.out;

import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;

import java.util.Optional;

public interface MemberPersistencePort {
    Optional<Member> getMemberById(Long id);
    Optional<Member> findByAuthIdAndAuthType(String authId, AuthType authType);
    Member save(Member member);
    boolean existsByAuthIdAndAuthType(String authId, AuthType authType);
    Optional<Member> findByAuthTypeAndProviderUserId(AuthType authType, String providerUserId);
}
