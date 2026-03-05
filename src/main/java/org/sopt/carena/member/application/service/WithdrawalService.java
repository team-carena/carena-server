package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.port.in.WithdrawalUseCase;
import org.sopt.carena.member.application.port.out.AccessTokenBlacklistStore;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.port.out.RefreshTokenBlacklistStore;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
import org.sopt.carena.member.application.service.util.JwtTokenParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalService implements WithdrawalUseCase {
    private final MemberPersistencePort memberRepository;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenParser jwtTokenParser;
    private final AccessTokenBlacklistStore accessTokenBlacklistStore;
    private final RefreshTokenBlacklistStore refreshTokenBlacklistStore;

    @Override
    public void withdrawal(final Long memberId,String accessToken,String refreshToken) {

        //멤버 관련 데이터 삭제
        memberRepository.deleteMemberAggregate(memberId);

        // Redis 블랙리스트 처리 - 실패해도 탈퇴는 성공
        // 탈퇴된 회원 토큰은 API 호출 시 MemberNotFoundException으로 자연 차단됨
        try {
            refreshTokenStore.delete(memberId);

            if (refreshToken != null) {
                long remaining = jwtTokenParser.getRemainingValidityMillis(refreshToken);
                if (remaining > 0) {
                    refreshTokenBlacklistStore.blacklist(refreshToken, remaining);
                }
            }

            long remaining = jwtTokenParser.getRemainingValidityMillis(accessToken);
            if (remaining > 0) {
                accessTokenBlacklistStore.blacklist(accessToken, remaining);
            }
        } catch (Exception e) {
            // Redis 장애 시에도 탈퇴는 완료로 처리 (토큰은 만료 시 자연 무효화, 탈퇴 회원 토큰은 API에서 차단되기 때문에 이렇게 판단함)
            log.warn("토큰 블랙리스트 처리 실패. memberId={}", memberId, e);
        }
    }
}

