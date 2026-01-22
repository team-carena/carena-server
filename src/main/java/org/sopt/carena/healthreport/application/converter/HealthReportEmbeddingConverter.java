package org.sopt.carena.healthreport.application.converter;

import java.util.stream.Collectors;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.healthreport.domain.status.RiskLevel;

public class HealthReportEmbeddingConverter {
	public static String toEmbeddingText(HealthReport healthReport) {
		return healthReport.getStatusCarriers().stream()
				.filter(carrier -> carrier.getRiskLevel() != RiskLevel.NONE && carrier.getRiskLevel() != RiskLevel.NORMAL)
				.map(HealthStatusCarrier::getDescription)
				.collect(Collectors.collectingAndThen(
						Collectors.joining(", "),
						s -> s.isEmpty() ? "건강 검진 결과 데이터가 존재하지 않음" : s
				));
	}
}
