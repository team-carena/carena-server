package org.sopt.carena.infrastructure.exception.code;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	LLM_DESERIALIZATION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "LLM 결과 역직렬화에 실패했습니다.");

	private final HttpStatus status;
	private final String message;
}
