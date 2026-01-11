package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.exception.member.DuplicateMemberException;
import org.sopt.carena.member.appliacation.port.in.SignupUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignupService implements SignupUseCase {

    private final JoinTokenStore joinTokenStore;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;

    @Override
    public SignupView signup(SignUpCommand command) {
        log.info("회원가입 시작 - name: {}", command.name());

        // tempToken 검증
        String oauthInfo = joinTokenStore.getAuthId(command.tempToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다"));
        log.info("tempToken 검증 완료 - authId: {}", oauthInfo);

        // OAuth 정보 파싱
        String[] parts = oauthInfo.split("\\|");
        AuthType authType = AuthType.valueOf(parts[0]);  // KAKAO
        String providerUserId = parts[1];                // 4683159314
        log.info("파싱 결과 - AuthType: {}, ProviderUserId: {}",
                authType, providerUserId);

        // 중복 회원 체크
        if (memberRepository.existsByAuthIdAndAuthType(providerUserId, AuthType.KAKAO)) {
            log.error("이미 가입된 회원입니다 - authId: {}", providerUserId);
            throw new DuplicateMemberException();
        }

        // 회원 생성
        Member member = Member.create(
                command.name(),
                command.birthdate(),
                command.gender(),
                providerUserId,
                AuthType.KAKAO
        );

        Member savedMember = memberRepository.save(member);
        log.info("회원 생성 완료 - memberId: {}", savedMember.getId());

        // tempToken 삭제
        joinTokenStore.delete(command.tempToken());

        // JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(savedMember.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(savedMember.getId());

        refreshTokenStore.save(
                savedMember.getId(),
                refreshToken,
                Duration.ofDays(14)
        );
        log.info("Refresh Token Redis 저장 완료 - memberId: {}", savedMember.getId());

        return new SignupView(
                accessToken,
                refreshToken,
                MemberView.from(savedMember)
        );
    }
}
