package org.sopt.carena.member.exception.oauth;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.exception.code.MemberErrorCode;

public class UnsupportedOAuthProviderException extends BaseException {
    public UnsupportedOAuthProviderException() {
        super(MemberErrorCode.UNSUPPORTED_OAUTH_PROVIDER);
    }
}