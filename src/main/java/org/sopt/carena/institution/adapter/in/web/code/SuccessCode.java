package org.sopt.carena.institution.adapter.in.web.code;

import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	ADDRESS_CODE_FOUND(HttpStatus.OK, "주소 코드 조회가 완료되었습니다."),
	INSTITUTION_FOUND(HttpStatus.OK, "검진 기관 조회가 완료되었습니다.");

	private final HttpStatus status;
	private final String message;
}
