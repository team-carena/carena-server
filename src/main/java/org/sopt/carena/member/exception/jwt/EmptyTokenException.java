package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class EmptyTokenException extends BaseException {
    public EmptyTokenException() {
        super(MemberErrorCode.EMPTY_TOKEN);
    }
}
