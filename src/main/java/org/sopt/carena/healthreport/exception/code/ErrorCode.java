package org.sopt.carena.healthreport.exception.code;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	HEALTH_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "건강 검진 데이터가 존재하지 않습니다."),
	MESSAGE_SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OCR 요청메시지 생성에 실패했습니다."),
	OCR_API_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "OCR API 호출에 실패했습니다."),
	ALREADY_EXIST_REPORT(HttpStatus.CONFLICT,"해당 날짜에 이미 건강검진 리포트가 존재합니다."),
	HEALTH_REPORT_EMBEDDING_NOT_FOUND(HttpStatus.NOT_FOUND,"건강검진 임베딩 결과가 존재하지 않습니다.");

	private final HttpStatus status;
	private final String message;
}
