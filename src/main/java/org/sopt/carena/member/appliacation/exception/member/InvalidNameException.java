package org.sopt.carena.member.appliacation.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class InvalidNameException extends BaseException {
    public InvalidNameException() {
        super(MemberErrorCode.INVALID_NAME);
    }
}