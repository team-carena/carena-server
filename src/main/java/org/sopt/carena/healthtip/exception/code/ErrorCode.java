package org.sopt.carena.healthtip.exception.code;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	HEALTH_TIP_NOTFOUND(HttpStatus.NOT_FOUND, "해당 ID의 건강팁을 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String message;
}
