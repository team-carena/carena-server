package org.sopt.carena.member.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class DuplicateMemberException extends BaseException {

    public DuplicateMemberException() {
        super(MemberErrorCode.DUPLICATE_MEMBER);
    }
}
