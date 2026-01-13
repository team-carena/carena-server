package org.sopt.carena.member.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class InvalidNameException extends BaseException {
    public InvalidNameException() {
        super(MemberErrorCode.INVALID_NAME);
    }
}