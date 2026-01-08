package org.sopt.carena.member.appliacation.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.MemberErrorCode;

public class UnsupportedOAuthProviderException extends BaseException {
    public UnsupportedOAuthProviderException() {
        super(MemberErrorCode.UNSUPPORTED_OAUTH_PROVIDER);
    }
}