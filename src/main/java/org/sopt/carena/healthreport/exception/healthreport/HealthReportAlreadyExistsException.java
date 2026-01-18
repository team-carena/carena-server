package org.sopt.carena.healthreport.exception.healthreport;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthreport.exception.code.ErrorCode;

public class HealthReportAlreadyExistsException extends BaseException {
    public HealthReportAlreadyExistsException() {
        super(ErrorCode.ALREADY_EXIST_REPORT);
    }
}
