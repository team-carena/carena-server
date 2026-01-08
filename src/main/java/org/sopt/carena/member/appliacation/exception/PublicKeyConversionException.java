package org.sopt.carena.member.appliacation.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.OAuthErrorCode;

public class PublicKeyConversionException extends BaseException {

    public PublicKeyConversionException() {
        super(OAuthErrorCode.PUBLIC_KEY_CONVERSION_FAILED);
    }

    public PublicKeyConversionException(Throwable cause) {
        super(OAuthErrorCode.PUBLIC_KEY_CONVERSION_FAILED, cause);
    }
}
