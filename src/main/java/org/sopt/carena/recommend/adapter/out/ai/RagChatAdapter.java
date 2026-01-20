package org.sopt.carena.recommend.adapter.out.ai;

import java.util.List;

import org.sopt.carena.healthreport.application.port.out.GetRagResultPort;
import org.sopt.carena.infrastructure.llm.client.RagChatClient;
import org.sopt.carena.infrastructure.llm.dto.RecommendedMealResult;
import org.sopt.carena.recommend.domain.RecommendedMeal;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RagChatAdapter implements GetRagResultPort {
	private final RagChatClient ragChatClient;

	// 여기서 도메인 반환으로 변경
	public RecommendedMealResult getRecommendedMeal(
			final String embeddingText,
			final List<Document> documents
	) {
		return ragChatClient.getRecommendedMeal(embeddingText, documents);

		// return RecommendedMeal.builder().build();
	}
}
