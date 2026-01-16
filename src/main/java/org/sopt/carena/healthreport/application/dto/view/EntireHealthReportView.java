package org.sopt.carena.healthreport.application.dto.view;

import java.time.LocalDate;
import java.util.List;

import org.sopt.carena.healthreport.domain.HealthReport;

public record EntireHealthReportView(
		long id,
		LocalDate healthCheckDate,
		List<DisplayElement> basic,
		List<DisplayElement> bloodPressure,
		List<DisplayElement> diabetes,
		List<DisplayElement> liver,
		List<DisplayElement> kidney,
		List<DisplayElement> anemia
) {
	public static EntireHealthReportView from(final HealthReport report) {
		return new EntireHealthReportView(
				report.getId(), report.getHealthCheckDate(),
				List.of(new DisplayElement("height", report.getHeight().value(), null),
						new DisplayElement("weight", report.getWeight().value(), null),
						new DisplayElement("waistCircumference", report.getWaistCircumference().value(), report.getWaistCircumference().status().getRiskLevel().getDescription()),
						new DisplayElement("bmi", report.getBmi().value(), report.getBmi().status().getRiskLevel().getDescription())),
				List.of(new DisplayElement("systolicBloodPressure", report.getBloodPressure().systolicBp(), report.getBloodPressure().systolicBpStatus().getRiskLevel().getDescription()),
						new DisplayElement("diastolicBloodPressure", report.getBloodPressure().diastolicBp(), report.getBloodPressure().diastolicBpStatus().getRiskLevel().getDescription())),
				List.of(new DisplayElement("fastingGlucose", report.getFastingGlucose().value(), report.getFastingGlucose().status().getRiskLevel().getDescription())),
				List.of(new DisplayElement("ast", report.getAst().value(), report.getAst().status().getRiskLevel().getDescription()),
						new DisplayElement("alt", report.getAlt().value(), report.getAlt().status().getRiskLevel().getDescription()),
						new DisplayElement("gammaGtp", report.getGammaGtp().value(), report.getGammaGtp().status().getRiskLevel().getDescription())),
				List.of(new DisplayElement("serumCreatinine", report.getSerumCreatinine().value(), report.getSerumCreatinine().status().getRiskLevel().getDescription()),
						new DisplayElement("egfr", report.getEgfr().value(), report.getEgfr().status().getRiskLevel().getDescription())),
				List.of(new DisplayElement("hemoglobin", report.getHemoglobin().value(), report.getHemoglobin().status().getRiskLevel().getDescription()))
		);
	}

	private record DisplayElement(
			String name,
			Number value,
			String riskLevel
	) {
	}
}
