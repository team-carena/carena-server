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
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignupService implements SignupUseCase {

    private final JoinTokenStore joinTokenStore;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public SignupView signup(SignUpCommand command) {
        log.info("회원가입 시작 - name: {}", command.name());

        // 1. tempToken 검증
        String authId = joinTokenStore.getAuthId(command.tempToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다"));

        log.info("tempToken 검증 완료 - authId: {}", authId);

        // 2. 중복 회원 체크
        if (memberRepository.existsByAuthIdAndAuthType(authId, AuthType.KAKAO)) {
            log.error("이미 가입된 회원입니다 - authId: {}", authId);
            throw new DuplicateMemberException();
        }

        // 3. 회원 생성
        Member member = Member.create(
                command.name(),
                command.birthdate(),
                command.gender(),
                authId
        );

        Member savedMember = memberRepository.save(member);
        log.info("회원 생성 완료 - memberId: {}", savedMember.getId());

        // 4. tempToken 삭제
        joinTokenStore.delete(command.tempToken());

        // 5. JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(savedMember.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return new SignupView(
                accessToken,
                refreshToken,
                MemberView.from(savedMember)
        );
    }
}
