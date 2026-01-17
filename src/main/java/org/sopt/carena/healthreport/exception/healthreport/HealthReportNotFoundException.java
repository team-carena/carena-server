package org.sopt.carena.healthreport.exception.healthreport;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthreport.exception.code.ErrorCode;

public class HealthReportNotFoundException extends BaseException {
	public HealthReportNotFoundException() {
		super(ErrorCode.HEALTH_REPORT_NOT_FOUND);
	}
}
