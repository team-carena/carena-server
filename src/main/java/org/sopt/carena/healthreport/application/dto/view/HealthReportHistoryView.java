package org.sopt.carena.healthreport.application.dto.view;

import java.util.List;

public record HealthReportHistoryView(
		List<HealthReportHistoryElement> history
) {
	public static HealthReportHistoryView from(final List<HealthReportHistoryElement> healthReportHistoryElements) {
		return new HealthReportHistoryView(healthReportHistoryElements);
	}
}
