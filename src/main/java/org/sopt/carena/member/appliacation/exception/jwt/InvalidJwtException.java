package org.sopt.carena.member.appliacation.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class InvalidJwtException extends BaseException {

    public InvalidJwtException() {
        super(MemberErrorCode.INVALID_JWT);
    }

    public InvalidJwtException(Throwable cause) {
        super(MemberErrorCode.INVALID_JWT, cause);
    }
}