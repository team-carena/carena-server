package org.sopt.carena.global.exception;

import org.sopt.carena.global.api.code.ErrorResultCode;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
	private final ErrorResultCode errorResultCode;

	protected BaseException(ErrorResultCode errorResultCode) {
		super(errorResultCode.getMessage());
		this.errorResultCode = errorResultCode;
	}

	protected BaseException(ErrorResultCode errorResultCode, String customMessage) {
		super(customMessage);
		this.errorResultCode = errorResultCode;
	}

	protected BaseException(ErrorResultCode errorResultCode, Throwable cause) {
		super(errorResultCode.getMessage(), cause);
		this.errorResultCode = errorResultCode;
	}

	protected BaseException(ErrorResultCode errorResultCode, String customMessage, Throwable cause) {
		super(customMessage, cause);
		this.errorResultCode = errorResultCode;
	}
}
