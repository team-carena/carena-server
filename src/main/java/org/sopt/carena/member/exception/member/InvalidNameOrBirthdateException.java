package org.sopt.carena.member.appliacation.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class InvalidNameOrBirthdateException extends BaseException {
    public InvalidNameOrBirthdateException() {
        super(MemberErrorCode.INVALID_NAME_OR_BIRTHDATE);
    }
}