package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.command.UpdateHealthReportCommand;

public interface UpdateHealthReportUseCase {
	void updateHealthReport(UpdateHealthReportCommand command);
}
