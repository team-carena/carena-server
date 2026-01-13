package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class UnsupportedTokenException extends BaseException {
    public UnsupportedTokenException() {
        super(MemberErrorCode.UNSUPPORTED_TOKEN);
    }
}
