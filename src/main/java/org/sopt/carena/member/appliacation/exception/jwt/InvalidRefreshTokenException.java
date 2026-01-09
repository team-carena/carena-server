package org.sopt.carena.member.appliacation.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class InvalidRefreshTokenException extends BaseException {

    public InvalidRefreshTokenException() {
        super(MemberErrorCode.INVALID_REFRESH_TOKEN);
    }
}