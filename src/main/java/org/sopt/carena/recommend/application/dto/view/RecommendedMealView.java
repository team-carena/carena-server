package org.sopt.carena.recommend.application.dto.view;

import org.sopt.carena.recommend.domain.RecommendedMeal;

public record RecommendedMealView(
		String recommendedMealId,
		String meal,
		String description,
		long baseDietDocument,
		String baseDietTitle
) {
	public static RecommendedMealView from(RecommendedMeal recommendedMeal) {
		return new RecommendedMealView(
				String.valueOf(recommendedMeal.getId()),
				recommendedMeal.getMeal(),
				recommendedMeal.getDescription(),
				recommendedMeal.getBaseDocumentId(),
				recommendedMeal.getBaseDocumentTitle()
		);
	}
}
