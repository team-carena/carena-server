package org.sopt.carena.institution.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.institution.exception.code.ErrorCode;

public class AddressCodeNotExistsException extends BaseException {
	public AddressCodeNotExistsException() {
		super(ErrorCode.SIDO_CODE_NOT_EXISTS);
	}
}
