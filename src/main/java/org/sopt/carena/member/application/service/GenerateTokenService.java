package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.dto.view.TokenGeneratedView;
import org.sopt.carena.member.application.port.in.GenerateTokenUseCase;
import org.sopt.carena.member.application.port.out.JoinTokenStore;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
import org.sopt.carena.member.application.service.util.JwtTokenGenerator;
import org.sopt.carena.member.domain.AuthType;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.sopt.carena.member.exception.member.InvalidTempTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenerateTokenService implements GenerateTokenUseCase {

    private final RefreshTokenStore refreshTokenStore;
    private final JoinTokenStore joinTokenStore;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final MemberPersistencePort memberPersistencePort;

    @Override
    public TokenGeneratedView generateToken(final String oneTimeToken) {

        String inputMemberInfo = joinTokenStore.getAuthId(oneTimeToken)
                .orElseThrow(InvalidTempTokenException::new);
        log.info("oneTimeToken 검증 완료 - authId: {}", inputMemberInfo);
        String[] parts = inputMemberInfo.split("\\|");
        AuthType authType = AuthType.valueOf(parts[0]);
        String providerUserId = parts[1];

        Optional<Member> member = memberPersistencePort.findByAuthTypeAndProviderUserId(authType,providerUserId);
        Long memberId = member.orElseThrow(MemberNotFoundException::new).getId();
        String AccessToken = jwtTokenGenerator.createAccessToken(memberId);
        String RefreshToken = jwtTokenGenerator.createRefreshToken(memberId);

        joinTokenStore.delete(oneTimeToken);
        refreshTokenStore.save(memberId, RefreshToken);
        return new TokenGeneratedView(AccessToken,RefreshToken);
    }
}
