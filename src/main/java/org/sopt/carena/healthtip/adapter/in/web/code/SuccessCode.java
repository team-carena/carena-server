package org.sopt.carena.healthtip.adapter.in.web.code;

import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	HEALTH_TIP_CREATED(HttpStatus.CREATED,"건강팁이 생성되었습니다."),
	HEALTH_TIP_FOUND(HttpStatus.OK, "건강팁 조회가 완료되었습니다."),
	HEALTH_TIP_UPDATED(HttpStatus.OK, "건강팁이 수정되었습니다."),
	HEALTH_TIP_DELETED(HttpStatus.OK, "건강팁이 삭제되었습니다.");

	private final HttpStatus status;
	private final String message;
}
