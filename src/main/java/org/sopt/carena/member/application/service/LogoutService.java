package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.application.port.in.LogoutUseCase;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
import org.sopt.carena.member.application.port.out.TokenBlacklistStore;
import org.sopt.carena.member.application.service.util.JwtTokenParser;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.domain.TokenType;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {
    private final MemberPersistencePort memberRepository;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenParser jwtTokenParser;
    private final TokenBlacklistStore tokenBlacklistStore;

    @Override
    public void logout(final Long memberId,String accessToken) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        refreshTokenStore.delete(memberId);

        // Access Token 블랙리스트 처리
        long remainingMillis =
                jwtTokenParser.getRemainingValidityMillis(accessToken);

        if (remainingMillis > 0) {
            tokenBlacklistStore
                    .blacklist(accessToken, TokenType.ACCESS,remainingMillis);
        }
    }
}
