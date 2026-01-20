package org.sopt.carena.recommend.adapter.in.web.controller;

import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.recommend.application.dto.view.RecommendedMealView;
import org.springframework.http.ResponseEntity;

public interface RecommendedMealApiDocs {
	ResponseEntity<SuccessResponse<Void>> createRecommendedMeal(long memberId, long healthReportId);

	ResponseEntity<SuccessResponse<RecommendedMealView>> getLatestRecommendedMeals(long memberId);
}
