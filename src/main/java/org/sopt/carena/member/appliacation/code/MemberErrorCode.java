package org.sopt.carena.member.appliacation.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorResultCode {
    INVALID_TEMP_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 임시 토큰입니다."),
    TEMP_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "임시 토큰이 만료되었습니다."),
    DUPLICATE_AUTH_ID(HttpStatus.CONFLICT, "이미 가입된 사용자입니다."),
    UNSUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth 제공자입니다.");


    private final HttpStatus status;
    private final String message;
}