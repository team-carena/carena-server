package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.dto.command.SignUpCommand;
import org.sopt.carena.member.application.dto.view.MemberView;
import org.sopt.carena.member.application.dto.view.SignupView;
import org.sopt.carena.member.application.service.util.JwtTokenGenerator;
import org.sopt.carena.member.exception.member.DuplicateMemberException;
import org.sopt.carena.member.exception.member.InvalidTempTokenException;
import org.sopt.carena.member.application.port.in.SignupUseCase;
import org.sopt.carena.member.application.port.out.JoinTokenStore;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
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
    private final MemberPersistencePort memberPersistencePort;
    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public SignupView signup(final SignUpCommand command) {
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
        if (memberPersistencePort.existsByAuthIdAndAuthType(providerUserId, authType)) {
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
        Member savedMember = memberPersistencePort.save(member);

        // tempToken 삭제
        joinTokenStore.delete(command.tempToken());
        String accessToken = jwtTokenGenerator.createAccessToken(savedMember.getId());
        String refreshToken = jwtTokenGenerator.createRefreshToken(savedMember.getId());

        refreshTokenStore.save(
                savedMember.getId(),
                refreshToken
        );

        return new SignupView(
                accessToken,
                refreshToken,
                MemberView.from(savedMember)
        );
    }
}
