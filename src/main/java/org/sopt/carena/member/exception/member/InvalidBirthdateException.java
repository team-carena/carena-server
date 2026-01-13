package org.sopt.carena.member.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class InvalidBirthdateException extends BaseException {
    public InvalidBirthdateException() {
        super(MemberErrorCode.INVALID_BIRTHDATE);
    }
}
