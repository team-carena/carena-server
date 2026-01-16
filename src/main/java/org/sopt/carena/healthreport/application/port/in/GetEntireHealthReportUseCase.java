package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.view.EntireHealthReportView;

public interface GetEntireHealthReportUseCase {
	EntireHealthReportView getEntireHealthReport(long memberId, long healthReportId);
}
