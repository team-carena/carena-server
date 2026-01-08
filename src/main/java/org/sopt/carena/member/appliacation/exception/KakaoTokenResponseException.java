package org.sopt.carena.member.appliacation.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.OAuthErrorCode;

public class KakaoTokenResponseException extends BaseException {
    public KakaoTokenResponseException() {
        super(OAuthErrorCode.KAKAO_TOKEN_RESPONSE_INVALID);
    }
}
