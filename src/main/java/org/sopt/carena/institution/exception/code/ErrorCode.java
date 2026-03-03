package org.sopt.carena.institution.exception.code;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	PUBLIC_DATA_ACCESS_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "공공데이터 로딩에 실패했습니다."),
	SIDO_CODE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 시/도 코드입니다.");

	private final HttpStatus status;
	private final String message;
}
