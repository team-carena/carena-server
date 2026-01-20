package org.sopt.carena.healthreport.application.port.out;

import java.util.List;

import org.sopt.carena.infrastructure.llm.dto.RecommendedMealResult;
import org.springframework.ai.document.Document;

public interface GetRagResultPort {
	RecommendedMealResult getRecommendedMeal(String embeddingText, List<Document> documents);
}
