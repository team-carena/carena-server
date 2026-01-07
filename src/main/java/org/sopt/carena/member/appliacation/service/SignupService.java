package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberView;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.port.in.SignupUseCase;
import org.sopt.carena.member.appliacation.port.out.JoinTokenStore;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
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

        // 2. 회원 생성
        Member member = Member.create(
                command.name(),
                command.birthdate(),
                command.gender(),
                authId
        );

        Member savedMember = memberRepository.save(member);
        log.info("회원 생성 완료 - memberId: {}", savedMember.getId());

        // 3. tempToken 삭제
        joinTokenStore.delete(command.tempToken());

        // 4. JWT 발급
        String accessToken = jwtTokenProvider.createToken(savedMember.getId());

        return new SignupView(
                accessToken,
                MemberView.from(savedMember)
        );
    }
}
