package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(MemberErrorCode.INVALID_TOKEN);
    }
}