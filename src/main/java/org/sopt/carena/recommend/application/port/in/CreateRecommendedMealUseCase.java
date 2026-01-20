package org.sopt.carena.recommend.application.port.in;

public interface CreateRecommendedMealUseCase {
	void saveRagResult(long memberId, long healthReportId);
}
