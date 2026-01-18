package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietRecommend;

import java.util.List;

public interface CreateDietRecommendUseCase {
    void createRecommendation(Long memberId, Long healthReportId);
    List<DietRecommend> createRecommendationsByEmbedding(
            Long memberId,
            Long healthReportId,
            float[] healthEmbedding
    );
}
