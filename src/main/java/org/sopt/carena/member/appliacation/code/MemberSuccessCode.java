package org.sopt.carena.member.appliacation.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements SuccessResultCode {

    LOGIN_URL_CREATED(HttpStatus.OK, "로그인 URL이 생성되었습니다."),
    SIGNUP_SUCCESS(HttpStatus.OK, "회원가입이 완료되었습니다."),
    TOKEN_REFRESHED(HttpStatus.OK, "새 엑세스 토큰이 발급되었습니다.");

    private final HttpStatus status;
    private final String message;
}