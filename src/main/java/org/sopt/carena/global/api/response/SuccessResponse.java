package org.sopt.carena.global.api.response;

import org.sopt.carena.global.api.code.SuccessResultCode;

public record SuccessResponse<T>(
		int status,
		String code,
		String message,
		T data
) {
	public static <T> SuccessResponse<T> of(SuccessResultCode successCode, T data) {
		return new SuccessResponse<>(
				successCode.getStatus().value(),
				successCode.toString(),
				successCode.getMessage(), data);
	}

	public static <T> SuccessResponse<T> of(SuccessResultCode successCode) {
		return new SuccessResponse<>(
				successCode.getStatus().value(),
				successCode.toString(),
				successCode.getMessage(), null);
	}
}
