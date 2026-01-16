package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.healthreport.domain.HealthReport;

public interface HealthReportPersistencePort {
	void saveHealthReport(HealthReport healthReport);
}
