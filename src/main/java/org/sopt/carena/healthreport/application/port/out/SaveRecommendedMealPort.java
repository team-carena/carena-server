package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.recommend.domain.RecommendedMeal;

public interface SaveRecommendedMealPort {
	RecommendedMeal saveRecommendedMeal(RecommendedMeal recommendedMeal);
}
