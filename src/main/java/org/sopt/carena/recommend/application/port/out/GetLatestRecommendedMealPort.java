package org.sopt.carena.recommend.application.port.out;

import java.util.Optional;

import org.sopt.carena.recommend.domain.RecommendedMeal;

public interface GetLatestRecommendedMealPort {
	Optional<RecommendedMeal> getRecommendedMealById(long memberId);
}
