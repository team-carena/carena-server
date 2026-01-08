package org.sopt.carena.member.appliacation.exception.oauth;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.OAuthErrorCode;

/**
 * 카카오 토큰 발급 요청 실패
 */
public class KakaoTokenRequestException extends BaseException {

    public KakaoTokenRequestException() {
        super(OAuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
    }

    public KakaoTokenRequestException(Throwable cause) {
        super(OAuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED, cause);
    }
}