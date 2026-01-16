package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryView;

public interface HealthReportItemHistoryUseCase {
	HealthReportHistoryView loadHeightHistory(long memberId);

	HealthReportHistoryView loadWeightHistory(long memberId);

	HealthReportHistoryView loadWaistCircumferenceHistory(long memberId);

	HealthReportHistoryView loadBmiHistory(long memberId);

	HealthReportHistoryView loadSystolicBpHistory(long memberId);

	HealthReportHistoryView loadDiastolicBpHistory(long memberId);

	HealthReportHistoryView loadHemoglobinHistory(long memberId);

	HealthReportHistoryView loadFastingGlucoseHistory(long memberId);

	HealthReportHistoryView loadAstHistory(long memberId);

	HealthReportHistoryView loadAltHistory(long memberId);

	HealthReportHistoryView loadGammaGtpHistory(long memberId);

	HealthReportHistoryView loadSerumCreatinineHistory(long memberId);

	HealthReportHistoryView loadEgfrHistory(long memberId);
}