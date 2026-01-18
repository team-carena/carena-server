package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietRecommend;

public interface CreateDietRecommendUseCase {
    DietRecommend createRecommendation(Long memberId);
}
