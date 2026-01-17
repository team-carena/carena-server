package org.sopt.carena.healthreport.exception.ocr;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthreport.exception.code.ErrorCode;

public class OcrMessageSerializationException extends BaseException {
	public OcrMessageSerializationException() {
		super(ErrorCode.MESSAGE_SERIALIZATION_ERROR);
	}
}
