/*
package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.adapter.out.external.kakao.dto.KakaoOAuthInfo;
import org.sopt.carena.member.appliacation.dto.view.KakaoLoginView;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.port.in.KakaoLoginUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.KakaoOAuthPort;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService implements KakaoLoginUseCase {

    private final KakaoOAuthPort kakaoOAuthPort;
    private final MemberRepository memberRepository;
    private final JoinTokenStore joinTokenStore;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public KakaoLoginView handleCallback(String code) {
        log.info("카카오 콜백 처리 시작 - code: {}", code);

        // 1. 인가코드로 ID Token 발급
        String idToken = kakaoOAuthPort.getIdToken(code);
        log.info("ID Token 발급 완료");

        // 2. ID Token 검증 및 파싱
        KakaoOAuthInfo oauthInfo = kakaoOAuthPort.verifyIdToken(idToken);
        String authId = oauthInfo.getSub();
        log.info("카카오 식별자 추출: {}", authId);

        // 3. 회원 조회
        Optional<Member> memberOpt = memberRepository
                .findByAuthIdAndAuthType(authId, AuthType.KAKAO);

        if (memberOpt.isPresent()) {
            // 4-A. 기존 회원
            Member member = memberOpt.get();
            log.info("기존 회원 로그인: memberId={}", member.getId());

            String accessToken= jwtTokenProvider.createAccessToken(member.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
            refreshTokenStore.save(
                    member.getId(),
                    refreshToken,
                    Duration.ofDays(14)
            );
            log.info("Refresh Token Redis 저장 완료 - memberId: {}", member.getId());


            return KakaoLoginView.forExistingMember(
                    accessToken,
                    refreshToken,
                    MemberView.from(member)
            );
        } else {
            // 4-B. 신규 사용자: tempToken 생성
            log.info("신규 사용자 - 회원가입 필요");

            String tempToken = UUID.randomUUID().toString();
            joinTokenStore.save(tempToken, authId, Duration.ofMinutes(10));

            return KakaoLoginView.forNewMember(tempToken);
        }
    }
}
*/
