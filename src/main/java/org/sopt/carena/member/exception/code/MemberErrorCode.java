package org.sopt.carena.member.exception.code;

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
    INVALID_NAME(HttpStatus.BAD_REQUEST, "이름은 20자 이하만 가능합니다."),
    //tempToken
    INVALID_TEMP_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 임시 토큰입니다."),
    // Token
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 token입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 형식이 올바르지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    TOKEN_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "토큰 서명 검증에 실패했습니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    // OAuth
    UNSUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth 제공자입니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}