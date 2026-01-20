package org.sopt.carena.healthreport.application.port.in;

import java.time.LocalDate;

import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryView;

public interface HealthReportItemHistoryUseCase {
	HealthReportHistoryView loadHeightHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadWeightHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadWaistCircumferenceHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadBmiHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadSystolicBpHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadDiastolicBpHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadHemoglobinHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadFastingGlucoseHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadAstHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadAltHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadGammaGtpHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadSerumCreatinineHistory(long memberId, LocalDate healthCheckDate);

	HealthReportHistoryView loadEgfrHistory(long memberId, LocalDate healthCheckDate);
}