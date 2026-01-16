package org.sopt.carena.healthreport.adapter.in.web.code;

import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	EXTRACT_TEXT_SUCCESS(HttpStatus.OK, "텍스트 추출이 완료되었습니다."),
	HEALTH_REPORT_CREATED(HttpStatus.CREATED, "건강 검진 기록이 생성되었습니다."),
	HEALTH_REPORT_FOUND(HttpStatus.OK, "건강 검진 기록 조회가 완료되었습니다.");

	private final HttpStatus status;
	private final String message;
}
