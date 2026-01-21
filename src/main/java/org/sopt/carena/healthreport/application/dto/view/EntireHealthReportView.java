package org.sopt.carena.healthreport.application.dto.view;

import java.time.LocalDate;
import java.util.List;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.status.RiskLevel;

public record EntireHealthReportView(
		String id,
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
				String.valueOf(report.getId()), report.getHealthCheckDate(),
				buildBasicInspection(report),
				buildBloodPressureInspection(report),
				buildDiabetesInspection(report),
				buildLiverInspection(report),
				buildKidneyInspection(report),
				buildAnemiaInspection(report)
		);
	}

	private static List<DisplayElement> buildBasicInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"height",
						report.getHeight().value(),
						null,
						null
				),
				new DisplayElement(
						"weight",
						report.getWeight().value(),
						null,
						null
				),
				new DisplayElement(
						"waistCircumference",
						report.getWaistCircumference().value(),
						report.getWaistCircumference().status().getRiskLevel(),
						report.getWaistCircumference().status().getRiskLevel().getDescription()
				),
				new DisplayElement("bmi",
						report.getBmi().value(),
						report.getBmi().status().getRiskLevel(),
						report.getBmi().status().getRiskLevel().getDescription()
				)
		);
	}

	private static List<DisplayElement> buildBloodPressureInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"systolicBloodPressure",
						report.getBloodPressure().systolicBp(),
						report.getBloodPressure().systolicBpStatus().getRiskLevel(),
						report.getBloodPressure().systolicBpStatus().getRiskLevel().getDescription()
				),
				new DisplayElement(
						"diastolicBloodPressure",
						report.getBloodPressure().diastolicBp(),
						report.getBloodPressure().diastolicBpStatus().getRiskLevel(),
						report.getBloodPressure().diastolicBpStatus().getRiskLevel().getDescription()
				)
		);
	}

	private static List<DisplayElement> buildDiabetesInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"fastingGlucose",
						report.getFastingGlucose().value(),
						report.getFastingGlucose().status().getRiskLevel(),
						report.getFastingGlucose().status().getRiskLevel().getDescription()
				)
		);
	}

	private static List<DisplayElement> buildLiverInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"ast",
						report.getAst().value(),
						report.getAst().status().getRiskLevel(),
						report.getAst().status().getRiskLevel().getDescription()),
				new DisplayElement(
						"alt",
						report.getAlt().value(),
						report.getAlt().status().getRiskLevel(),
						report.getAlt().status().getRiskLevel().getDescription()
				),
				new DisplayElement(
						"gammaGtp",
						report.getGammaGtp().value(),
						report.getGammaGtp().status().getRiskLevel(),
						report.getGammaGtp().status().getRiskLevel().getDescription()
				)
		);
	}

	private static List<DisplayElement> buildKidneyInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"serumCreatinine",
						report.getSerumCreatinine().value(),
						report.getSerumCreatinine().status().getRiskLevel(),
						report.getSerumCreatinine().status().getRiskLevel().getDescription()
				),
				new DisplayElement(
						"egfr",
						report.getEgfr().value(),
						report.getEgfr().status().getRiskLevel(),
						report.getEgfr().status().getRiskLevel().getDescription()
				)
		);
	}

	private static List<DisplayElement> buildAnemiaInspection(final HealthReport report) {
		return List.of(
				new DisplayElement(
						"hemoglobin",
						report.getHemoglobin().value(),
						report.getHemoglobin().status().getRiskLevel(),
						report.getHemoglobin().status().getRiskLevel().getDescription()
				));
	}

	private record DisplayElement(
			String name,
			Number value,
			RiskLevel riskLevelLabel,
			String riskLevelValue
	) {
	}
}
