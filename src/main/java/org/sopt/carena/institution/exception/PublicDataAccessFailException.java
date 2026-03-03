package org.sopt.carena.institution.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.institution.exception.code.ErrorCode;

public class PublicDataAccessFailException extends BaseException {
	public PublicDataAccessFailException() {
		super(ErrorCode.PUBLIC_DATA_ACCESS_FAIL);
	}
}
