package org.sopt.carena.recommend.application.service;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.sopt.carena.diet.domain.DietInformation;
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
import org.sopt.carena.recommend.exception.DocumentNotExistException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateRecommendedMealService implements CreateRecommendedMealUseCase {
	private final GetDocumentListPort getDocumentListPort;
	private final SaveRecommendedMealPort saveRecommendedMealPort;
	private final GetRagResultPort getRagResultPort;
	private final LoadHealthReportPort loadHealthReportPort;
	private final ExecutorService virtualExecutorService;

	public void saveRagResult(final long memberId) {
		HealthReport healthReport = loadHealthReportPort.findLatestHealthReportByMemberId(memberId)
				.orElseThrow(HealthReportNotFoundException::new);

		String embeddingText = HealthReportEmbeddingConverter.toEmbeddingText(healthReport);

		List<DietInformation> documents = getDocumentListPort.searchDocumentsId(embeddingText, 5);

		if (documents.isEmpty()) {
			throw new DocumentNotExistException();
		}

		System.out.println("===식단 추천 시작===");
		virtualExecutorService.submit(
				() -> saveRagResultAsync(embeddingText, documents, memberId, healthReport.getId()));
	}

	private void saveRagResultAsync(
			final String embeddingText,
			final List<DietInformation> documents,
			final long memberId,
			final long healthReportId
	) {
		try{
			Long baseDocumentId = documents.getFirst().getId();
			String baseDocumentTitle = documents.getFirst().getTitle();

			List<String> documentContents = documents.stream()
					.map(DietInformation::getCombinedChunkContent)
					.toList();

			RecommendedMealResult result = getRagResultPort.getRecommendedMeal(embeddingText, documentContents);

			log.info("LLM 추천 식단 생성 완료, 저장 호출");

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
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(e);
		}
	}
}
