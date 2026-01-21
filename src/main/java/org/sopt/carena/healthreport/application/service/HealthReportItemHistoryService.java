package org.sopt.carena.healthreport.application.service;

import java.time.LocalDate;
import java.util.List;

import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryElement;
import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryView;
import org.sopt.carena.healthreport.application.port.in.HealthReportItemHistoryUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthReportItemHistoryService implements HealthReportItemHistoryUseCase {
	private final HealthReportPersistencePort healthReportPersistencePort;

	public HealthReportHistoryView loadHeightHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsHeightIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getHeight().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadWeightHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsWeightIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getWeight().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadWaistCircumferenceHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsWaistCircumferenceIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getWaistCircumference().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadBmiHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsBmiIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getBmi().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadSystolicBpHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsSystolicBpIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getBloodPressure().systolicBp(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadDiastolicBpHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsDiastolicBpIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getBloodPressure().diastolicBp(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadHemoglobinHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsHemoglobinIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getHemoglobin().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadFastingGlucoseHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsFastingGlucoseIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getFastingGlucose().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadAstHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsAstIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getAst().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadAltHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsAltIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getAlt().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadGammaGtpHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsGammaGtpIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getGammaGtp().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadSerumCreatinineHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsSerumCreatinineIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getSerumCreatinine().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}

	public HealthReportHistoryView loadEgfrHistory(final long memberId, final LocalDate healthCheckDate) {
		List<HealthReportHistoryElement> elements =
				healthReportPersistencePort.findLatestHealthReportsEgfrIsNotNullByMemberId(memberId, healthCheckDate)
				.stream()
				.map(healthReport -> HealthReportHistoryElement.of(
						healthReport.getEgfr().value(),
						healthReport.getHealthCheckDate()))
				.toList();

		return HealthReportHistoryView.from(elements);
	}
}
