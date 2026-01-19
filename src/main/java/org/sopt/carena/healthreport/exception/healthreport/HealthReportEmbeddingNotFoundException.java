package org.sopt.carena.healthreport.exception.healthreport;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.healthreport.exception.code.ErrorCode;

public class HealthReportEmbeddingNotFoundException extends BaseException {
    public HealthReportEmbeddingNotFoundException() {
        super(ErrorCode.HEALTH_REPORT_EMBEDDING_NOT_FOUND);
    }
}
