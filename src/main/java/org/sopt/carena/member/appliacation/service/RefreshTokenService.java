package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;
import org.sopt.carena.member.appliacation.exception.jwt.InvalidRefreshTokenException;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenRefreshView refreshAccessToken(String refreshToken) {
        log.info("Access Token 재발급 요청");

        // 1. Refresh Token 검증
        jwtTokenProvider.validateToken(refreshToken);

        // 2. Refresh Token에서 memberId 추출
        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);
        log.debug("Refresh Token에서 memberId 추출: {}", memberId);

        // 3. Redis에 저장된 Refresh Token과 비교
        String storedRefreshToken = refreshTokenStore.get(memberId)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!refreshToken.equals(storedRefreshToken)) {
            log.error("Refresh Token 불일치 - memberId: {}", memberId);
            throw new InvalidRefreshTokenException();
        }

        // 4. 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(memberId);
        log.info("Access Token 재발급 완료 - memberId: {}", memberId);

        return new TokenRefreshView(newAccessToken);
    }
}