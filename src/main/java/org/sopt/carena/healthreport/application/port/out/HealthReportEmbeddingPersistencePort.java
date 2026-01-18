package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.healthreport.domain.HealthReportEmbedding;

public interface HealthReportEmbeddingPersistencePort {
	void saveHealthReportEmbedding(HealthReportEmbedding healthReportEmbedding);
}
