package org.sopt.carena.recommend.application.service;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.sopt.carena.healthreport.application.converter.HealthReportEmbeddingConverter;
import org.sopt.carena.healthreport.application.port.out.GetRagResultPort;
import org.sopt.carena.healthreport.application.port.out.SaveRecommendedMealPort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.sopt.carena.infrastructure.llm.dto.RecommendedMealResult;
import org.sopt.carena.recommend.application.port.in.CreateRecommendedMealUseCase;
import org.sopt.carena.recommend.application.port.out.GetDocumentListPort;
import org.sopt.carena.recommend.application.port.out.LoadHealthReportPort;
import org.sopt.carena.recommend.domain.RecommendedMeal;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRecommendedMealService implements CreateRecommendedMealUseCase {
	private final GetDocumentListPort getDocumentListPort;
	private final SaveRecommendedMealPort saveRecommendedMealPort;
	private final GetRagResultPort getRagResultPort;
	private final LoadHealthReportPort loadHealthReportPort;
	private final ExecutorService virtualExecutorService;

	public void saveRagResult(final long memberId, final long healthReportId) {
		HealthReport healthReport = loadHealthReportPort.findByMemberIdAndHealthReportId(memberId, healthReportId)
				.orElseThrow(HealthReportNotFoundException::new);

		String embeddingText = HealthReportEmbeddingConverter.toEmbeddingText(healthReport);

		List<Document> documents = getDocumentListPort.searchDocuments(embeddingText, 5);

		virtualExecutorService.submit(() -> saveRagResultAsync(embeddingText, documents, memberId, healthReportId));
	}

	private void saveRagResultAsync(
			final String embeddingText,
			final List<Document> documents,
			final long memberId,
			final long healthReportId
	) {
		Long baseDocumentId = (Long)documents.get(0).getMetadata().get("document_id");
		String baseDocumentTitle = (String)documents.get(0).getMetadata().get("title");

		RecommendedMealResult result = getRagResultPort.getRecommendedMeal(embeddingText, documents);

		RecommendedMeal recommendedMeal = RecommendedMeal.builder()
				.meal(result.meal())
				.description(result.description())
				.baseDocumentId(baseDocumentId)
				.baseDocumentTitle(baseDocumentTitle)
				.memberId(memberId)
				.healthReportId(healthReportId)
				.build();

		// rag 결과 저장
		saveRecommendedMealPort.saveRecommendedMeal(recommendedMeal);
	}
}
