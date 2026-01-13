package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {
        super(MemberErrorCode.EXPIRED_TOKEN);
    }
}
