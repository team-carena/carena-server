package org.sopt.carena.member.exception.member;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class InvalidTempTokenException extends BaseException {
    public InvalidTempTokenException() {
        super(MemberErrorCode.INVALID_TEMP_TOKEN);
    }
}