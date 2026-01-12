package org.sopt.carena.member.appliacation.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorResultCode {

    // 회원가입
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    INVALID_BIRTHDATE(HttpStatus.BAD_REQUEST, "1960 ~ 2007 사이의 생년월일만 입력 가능합니다."),
    INVALID_NAME_OR_BIRTHDATE(HttpStatus.BAD_REQUEST, "이름은 20자 이하, 생년월일은 1960 ~ 2007 사이로 입력해주세요"),
    //tempToken
    INVALID_TEMP_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 임시 토큰입니다."),
    // Token
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 token입니다."),
    // OAuth
    UNSUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth 제공자입니다.");

    private final HttpStatus status;
    private final String message;
}