package org.sopt.carena.recommend.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.recommend.exception.code.ErrorCode;

public class DocumentNotExistException extends BaseException {
	public DocumentNotExistException() {
		super(ErrorCode.DOCUMENT_NOT_EXIST);
	}
}
