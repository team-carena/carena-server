package org.sopt.carena.global.exception.handler;

import org.sopt.carena.global.api.code.ErrorResultCode;
import org.sopt.carena.global.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public abstract class BaseExceptionHandler {
	protected final ResponseEntity<ApiResponse> buildErrorResponse(ErrorResultCode errorResultCode) {
		return ResponseEntity.status(errorResultCode.getStatus())
				.body(ApiResponse.failure(errorResultCode));
	}
}