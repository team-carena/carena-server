package org.sopt.carena.recommend.adapter.in.web.code;

import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	RECOMMENDED_MEAL_FOUND(HttpStatus.OK, "맞춤 식단 조회가 완료되었습니다."),
	RECOMMENDED_MEAL_CREATED(HttpStatus.CREATED, "맞춤 식단 생성이 요청되었습니다.");

	private final HttpStatus status;
	private final String message;
}
