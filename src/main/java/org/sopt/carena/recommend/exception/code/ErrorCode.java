package org.sopt.carena.recommend.exception.code;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	RECOMMENDED_MEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "맞춤 식단 정보가 존재하지 않습니다."),
	DOCUMENT_NOT_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "맞춤 식단을 위한 관련 데이터가 존재하지 않습니다.");

	private final HttpStatus status;
	private final String message;
}
