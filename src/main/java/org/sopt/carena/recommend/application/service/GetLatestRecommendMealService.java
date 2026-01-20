package org.sopt.carena.recommend.application.service;

import org.sopt.carena.recommend.application.dto.view.RecommendedMealView;
import org.sopt.carena.recommend.application.port.in.GetLatestRecommendedMealUseCase;
import org.sopt.carena.recommend.application.port.out.GetLatestRecommendedMealPort;
import org.sopt.carena.recommend.domain.RecommendedMeal;
import org.sopt.carena.recommend.exception.RecommendedMealNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetLatestRecommendMealService implements GetLatestRecommendedMealUseCase {
	private final GetLatestRecommendedMealPort getLatestRecommendedMealPort;

	public RecommendedMealView getLatestRecommendedMeal(final long memberId) {
		RecommendedMeal recommendedMeal = getLatestRecommendedMealPort.getRecommendedMealById(memberId)
				.orElseThrow(RecommendedMealNotFoundException::new);

		return RecommendedMealView.from(recommendedMeal);
	}
}
