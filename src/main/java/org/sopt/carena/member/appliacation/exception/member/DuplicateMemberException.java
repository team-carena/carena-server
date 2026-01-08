package org.sopt.carena.member.appliacation.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class DuplicateMemberException extends BaseException {

    public DuplicateMemberException() {
        super(MemberErrorCode.DUPLICATE_MEMBER);
    }

}
