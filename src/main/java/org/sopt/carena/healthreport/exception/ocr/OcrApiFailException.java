package org.sopt.carena.healthreport.exception.ocr;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthreport.exception.code.ErrorCode;

public class OcrApiFailException extends BaseException {
	public OcrApiFailException() {
		super(ErrorCode.OCR_API_FAIL);
	}
}
