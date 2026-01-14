package org.sopt.carena.member.adapter.in.web.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements SuccessResultCode {

    SIGNUP_SUCCESS(HttpStatus.OK, "회원가입이 완료되었습니다."),
    TOKEN_REFRESHED(HttpStatus.OK, "새 엑세스 토큰이 발급되었습니다."),
    MEMBER_IFNO(HttpStatus.OK,"사용자 정보가 조회되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 처리가 완료되었습니다.");

    private final HttpStatus status;
    private final String message;
}