package org.sopt.carena.member.application.port.out;

import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;

import java.util.Optional;

public interface MemberPersistencePort {
    Member save(Member member);
    boolean existsByAuthIdAndAuthType(String authId, AuthType authType);
    Optional<Member> findByAuthTypeAndProviderUserId(AuthType authType, String providerUserId);
}
