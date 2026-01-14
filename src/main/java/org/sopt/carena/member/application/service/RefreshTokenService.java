package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.dto.view.TokenRefreshView;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.application.service.util.JwtTokenGenerator;
import org.sopt.carena.member.application.service.util.JwtTokenParser;
import org.sopt.carena.member.application.service.util.JwtTokenValidator;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.domain.Role;
import org.sopt.carena.member.exception.jwt.InvalidTokenException;
import org.sopt.carena.member.application.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.application.port.out.RefreshTokenStore;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService implements RefreshTokenUseCase {

    private final RefreshTokenStore refreshTokenStore;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenParser jwtTokenParser;
    private final JwtTokenValidator jwtTokenValidator;
    private final MemberPersistencePort memberPersistencePort;

    @Override
    public TokenRefreshView refreshAccessToken(final String refreshToken) {
        log.info("Access Token 재발급 요청");
        jwtTokenValidator.validateToken(refreshToken);

        Long memberId = jwtTokenParser.getMemberId(refreshToken);

        String storedRefreshToken = refreshTokenStore.get(memberId)
                .orElseThrow(InvalidTokenException::new);

        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidTokenException();
        }

        // DB에서 최신 Role 조회
        Member member = memberPersistencePort.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Role currentRole = member.getRole();
        String newAccessToken = jwtTokenGenerator.createAccessToken(memberId,currentRole);
        String newRefreshToken = jwtTokenGenerator.createRefreshToken(memberId,currentRole);

        refreshTokenStore.delete(memberId);
        refreshTokenStore.save(memberId, newRefreshToken);
        return new TokenRefreshView(newAccessToken,newRefreshToken);
    }
}