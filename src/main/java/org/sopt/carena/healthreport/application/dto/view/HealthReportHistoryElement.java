package org.sopt.carena.healthreport.application.dto.view;

import java.time.LocalDate;

public record HealthReportHistoryElement(
		Number value,
		LocalDate healthCheckDate
) {
	public static HealthReportHistoryElement of(final Number value, final LocalDate healthCheckDate) {
		return new HealthReportHistoryElement(value, healthCheckDate);
	}
}
