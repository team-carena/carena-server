package org.sopt.carena.recommend.adapter.in.web.controller;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.recommend.adapter.in.web.code.SuccessCode;
import org.sopt.carena.recommend.application.dto.view.RecommendedMealView;
import org.sopt.carena.recommend.application.port.in.CreateRecommendedMealUseCase;
import org.sopt.carena.recommend.application.port.in.GetLatestRecommendedMealUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/recommend")
public class RecommendedMealController {
	private final CreateRecommendedMealUseCase createRecommendedMealUseCase;
	private final GetLatestRecommendedMealUseCase getLatestRecommendedMealUseCase;

	@PostMapping
	public ResponseEntity<SuccessResponse<Void>> createRecommendedMeal(
			@AuthenticationPrincipal final long memberId
	) {
		createRecommendedMealUseCase.saveRagResult(memberId);

		return ResponseEntity.status(SuccessCode.RECOMMENDED_MEAL_CREATED.getStatus())
				.body(ApiResponse.success(SuccessCode.RECOMMENDED_MEAL_CREATED));
	}

	@GetMapping
	public ResponseEntity<SuccessResponse<RecommendedMealView>> getLatestRecommendedMeals(
			@AuthenticationPrincipal final long memberId
	) {
		return ResponseEntity.status(SuccessCode.RECOMMENDED_MEAL_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.RECOMMENDED_MEAL_FOUND,
						getLatestRecommendedMealUseCase.getLatestRecommendedMeal(memberId)));
	}
}
