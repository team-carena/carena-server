package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class MalformedTokenException extends BaseException {
    public MalformedTokenException() {
        super(MemberErrorCode.MALFORMED_TOKEN);
    }
}
