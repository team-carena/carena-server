package org.sopt.carena.recommend.application.dto.view;

import org.sopt.carena.recommend.domain.RecommendedMeal;

public record RecommendedMealView(
		String recommendedMealId,
		String meal,
		String description,
		String baseDietDocumentId,
		String baseDietTitle
) {
	public static RecommendedMealView from(RecommendedMeal recommendedMeal) {
		return new RecommendedMealView(
				String.valueOf(recommendedMeal.getId()),
				recommendedMeal.getMeal(),
				recommendedMeal.getDescription(),
				String.valueOf(recommendedMeal.getBaseDocumentId()),
				recommendedMeal.getBaseDocumentTitle()
		);
	}
}
