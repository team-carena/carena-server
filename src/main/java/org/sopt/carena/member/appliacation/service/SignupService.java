package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.exception.member.DuplicateMemberException;
import org.sopt.carena.member.appliacation.exception.member.InvalidTempTokenException;
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
                .orElseThrow(InvalidTempTokenException::new);
        log.info("tempToken 검증 완료 - authId: {}", oauthInfo);

        // OAuth 정보 파싱
        String[] parts = oauthInfo.split("\\|");
        AuthType authType = AuthType.valueOf(parts[0]);
        String providerUserId = parts[1];

        // 중복 회원 체크
        if (memberRepository.existsByAuthIdAndAuthType(providerUserId, authType)) {
            throw new DuplicateMemberException();
        }

        // 회원 생성
        Member member = Member.create(
                command.name(),
                command.birthdate(),
                command.gender(),
                providerUserId,
                authType
        );
        Member savedMember = memberRepository.save(member);

        // tempToken 삭제
        joinTokenStore.delete(command.tempToken());
        String accessToken = jwtTokenProvider.createAccessToken(savedMember.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(savedMember.getId());

        refreshTokenStore.save(
                savedMember.getId(),
                refreshToken,
                Duration.ofDays(14)
        );

        return new SignupView(
                accessToken,
                refreshToken,
                MemberView.from(savedMember)
        );
    }
}
