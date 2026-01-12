package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;
import org.sopt.carena.member.appliacation.service.util.JwtTokenGenerator;
import org.sopt.carena.member.appliacation.service.util.JwtTokenParser;
import org.sopt.carena.member.appliacation.service.util.JwtTokenValidator;
import org.sopt.carena.member.exception.jwt.InvalidTokenException;
import org.sopt.carena.member.appliacation.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.appliacation.port.out.RefreshTokenStore;
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

    @Override
    public TokenRefreshView refreshAccessToken(String refreshToken) {
        log.info("Access Token 재발급 요청");
        jwtTokenValidator.validateToken(refreshToken);

        Long memberId = jwtTokenParser.getMemberId(refreshToken);

        // Redis에 저장된 Refresh Token과 비교
        String storedRefreshToken = refreshTokenStore.get(memberId)
                .orElseThrow(InvalidTokenException::new);

        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidTokenException();
        }
        String newAccessToken = jwtTokenGenerator.createAccessToken(memberId);
        return new TokenRefreshView(newAccessToken);
    }
}