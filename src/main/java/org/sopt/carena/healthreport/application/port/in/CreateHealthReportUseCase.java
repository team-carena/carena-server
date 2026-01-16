package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.commend.CreateHealthReportCommand;

public interface CreateHealthReportUseCase {
	void createHealthReport(CreateHealthReportCommand commend);
}
