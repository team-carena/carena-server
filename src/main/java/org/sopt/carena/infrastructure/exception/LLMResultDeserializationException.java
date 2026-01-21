package org.sopt.carena.infrastructure.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.infrastructure.exception.code.ErrorCode;

public class LLMResultDeserializationException extends BaseException {
	public LLMResultDeserializationException() {
		super(ErrorCode.LLM_DESERIALIZATION_FAIL);
	}
}
