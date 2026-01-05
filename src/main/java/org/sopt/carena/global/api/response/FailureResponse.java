package org.sopt.carena.global.api.response;

import org.sopt.carena.global.api.code.ErrorResultCode;

public record FailureResponse(
		int status,
		String code,
		String message
) implements ApiResponse {
	public static FailureResponse of(ErrorResultCode errorCode) {
		return new FailureResponse(
				errorCode.getStatus().value(),
				errorCode.toString(),
				errorCode.getMessage());
	}

	public static FailureResponse of(ErrorResultCode errorCode, String message) {
		return new FailureResponse(
				errorCode.getStatus().value(),
				errorCode.toString(),
				message
		);
	}
}