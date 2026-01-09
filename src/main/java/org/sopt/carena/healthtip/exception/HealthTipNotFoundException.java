package org.sopt.carena.healthtip.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthtip.exception.code.ErrorCode;

public class HealthTipNotFoundException extends BaseException {
	public HealthTipNotFoundException() {
		super(ErrorCode.HEALTH_TIP_NOTFOUND);
	}
}
