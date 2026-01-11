package org.sopt.carena.member.appliacation.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(MemberErrorCode.INVALID_TOKEN);
    }
}