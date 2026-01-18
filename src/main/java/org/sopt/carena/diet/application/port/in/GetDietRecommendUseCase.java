package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietRecommend;

import java.util.Optional;

public interface GetDietRecommendUseCase {
    Optional<DietRecommend> getLatestRecommendation(Long memberId);
}
