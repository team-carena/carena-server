package org.sopt.carena.member.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class InvalidNameOrBirthdateException extends BaseException {
    public InvalidNameOrBirthdateException() {
        super(MemberErrorCode.INVALID_NAME_OR_BIRTHDATE);
    }
}