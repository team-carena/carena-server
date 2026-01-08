package org.sopt.carena.member.appliacation.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OAuthErrorCode implements ErrorResultCode {

    // ID Token 검증
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 ID Token입니다."),
    EXPIRED_ID_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 ID Token입니다."),
    ID_TOKEN_MISSING_KID(HttpStatus.BAD_REQUEST, "ID Token에 kid가 없습니다."),

    // JWKS 조회
    JWKS_RETRIEVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 공개키 조회에 실패했습니다."),
    JWKS_KEY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "일치하는 공개키를 찾을 수 없습니다."),

    // RSA 변환
    PUBLIC_KEY_CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "공개키 변환에 실패했습니다."),

    // 카카오 토큰 발급
    KAKAO_TOKEN_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 토큰 발급 요청에 실패했습니다."),
    KAKAO_TOKEN_RESPONSE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 토큰 응답이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
