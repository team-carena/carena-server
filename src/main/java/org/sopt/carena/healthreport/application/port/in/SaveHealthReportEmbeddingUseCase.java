package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.domain.Member;

public interface SaveHealthReportEmbeddingUseCase {
	void embeddingAndSave(final String embeddingText, final Member member, final HealthReport healthReport);
}
