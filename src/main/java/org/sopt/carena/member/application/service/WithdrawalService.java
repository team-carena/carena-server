package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.member.application.port.in.WithdrawalUseCase;
import org.sopt.carena.member.application.port.out.AccessTokenBlacklistStore;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.port.out.RefreshTokenBlacklistStore;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
import org.sopt.carena.member.application.service.util.JwtTokenParser;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalService implements WithdrawalUseCase {
    private final MemberPersistencePort memberRepository;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenParser jwtTokenParser;
    private final AccessTokenBlacklistStore accessTokenBlacklistStore;
    private final RefreshTokenBlacklistStore refreshTokenBlacklistStore;

    @Override
    public void withdrawal(final Long memberId,String accessToken,String refreshToken) {

        //refresh token 블랙리스팅 처리
        refreshTokenStore.delete(memberId);
        if(refreshToken != null) {
            long refreshremainingMillis =
                    jwtTokenParser.getRemainingValidityMillis(refreshToken);

            refreshTokenBlacklistStore.blacklist(refreshToken, refreshremainingMillis);
        }

        // Access Token 블랙리스트 처리
        long remainingMillis =
                jwtTokenParser.getRemainingValidityMillis(accessToken);

        if (remainingMillis > 0) {
            accessTokenBlacklistStore
                    .blacklist(accessToken, remainingMillis);
        }
        //멤버 관련 데이터 삭제
        memberRepository.deleteMemberAggregate(memberId);
    }
}

