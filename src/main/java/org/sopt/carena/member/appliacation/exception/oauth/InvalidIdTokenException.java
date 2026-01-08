package org.sopt.carena.member.appliacation.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.OAuthErrorCode;

/**
 * ID Token 검증 실패 예외
 */
public class InvalidIdTokenException extends BaseException {

    public InvalidIdTokenException() {
        super(OAuthErrorCode.INVALID_ID_TOKEN);
    }

    public InvalidIdTokenException(String message) {
        super(OAuthErrorCode.INVALID_ID_TOKEN, message);
    }

    public InvalidIdTokenException(Throwable cause) {
        super(OAuthErrorCode.INVALID_ID_TOKEN, cause);
    }
}
