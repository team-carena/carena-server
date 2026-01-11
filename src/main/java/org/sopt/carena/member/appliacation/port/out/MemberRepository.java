package org.sopt.carena.member.appliacation.port.out;

import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByAuthIdAndAuthType(String authId, AuthType authType);
    Member save(Member member);
    boolean existsByAuthIdAndAuthType(String authId, AuthType authType);
    Optional<Member> findByAuthTypeAndProviderUserId(AuthType authType, String providerUserId);
}
