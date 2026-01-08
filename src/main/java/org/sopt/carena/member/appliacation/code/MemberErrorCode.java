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
    INVALID_NAME(HttpStatus.BAD_REQUEST, "20자 이하로 입력해주세요."),

    //tempToken
    INVALID_TEMP_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 임시 토큰입니다."),

    // Refresh Token
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Refresh Token을 찾을 수 없습니다."),

    // JWT
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    DUPLICATE_AUTH_ID(HttpStatus.CONFLICT, "이미 가입된 사용자입니다."),

    // OAuth
    UNSUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth 제공자입니다.");


    private final HttpStatus status;
    private final String message;
}