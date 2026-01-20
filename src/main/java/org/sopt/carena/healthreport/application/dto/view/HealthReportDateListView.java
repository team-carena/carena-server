package org.sopt.carena.healthreport.application.dto.view;

import java.time.LocalDate;
import java.util.List;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.springframework.data.domain.Slice;

public record HealthReportDateListView(
		List<ReportDateInfo> reportDates,
		boolean hasNext
) {
	public static HealthReportDateListView from(final Slice<HealthReport> healthReports) {
		List<ReportDateInfo> reportDates = healthReports.getContent().stream()
				.map(ReportDateInfo::from)
				.toList();

		return new HealthReportDateListView(reportDates, healthReports.hasNext());
	}

	private record ReportDateInfo(
			Long healthReportId,
			LocalDate healthCheckDate,
			String institutionName
	) {
		private static ReportDateInfo from(final HealthReport healthReport) {
			return new ReportDateInfo(
					healthReport.getId(),
					healthReport.getHealthCheckDate(),
					healthReport.getInstitutionName());
		}

	}
}
