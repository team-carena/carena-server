package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class TokenSignatureException extends BaseException {
    public TokenSignatureException() {
        super(MemberErrorCode.TOKEN_SIGNATURE_ERROR);
    }
}
