package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;

public interface HealthReportEmbeddingPersistencePort {
	void saveHealthReportEmbedding(HealthReportEmbedding healthReportEmbedding);
	HealthReportEmbedding findByHealthReportId(Long healthReportId);
	HealthReport findLatestByMemberId(Long memberId);
}
