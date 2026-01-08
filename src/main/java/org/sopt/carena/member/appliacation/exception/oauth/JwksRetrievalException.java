package org.sopt.carena.member.appliacation.exception.oauth;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.member.appliacation.code.OAuthErrorCode;

public class JwksRetrievalException extends BaseException {

    public JwksRetrievalException() {
        super(OAuthErrorCode.JWKS_RETRIEVAL_FAILED);
    }

    public JwksRetrievalException(String message) {
        super(OAuthErrorCode.JWKS_RETRIEVAL_FAILED, message);
    }

    public JwksRetrievalException(Throwable cause) {
        super(OAuthErrorCode.JWKS_RETRIEVAL_FAILED, cause);
    }
}