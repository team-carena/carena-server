package org.sopt.carena.recommend.application.port.in;

import org.sopt.carena.recommend.application.dto.view.RecommendedMealView;

public interface GetLatestRecommendedMealUseCase {
	RecommendedMealView getLatestRecommendedMeal(long memberId);
}
