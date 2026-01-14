package org.sopt.carena.member.exception.jwt;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
