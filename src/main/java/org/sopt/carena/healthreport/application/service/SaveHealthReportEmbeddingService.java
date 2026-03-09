package org.sopt.carena.healthreport.application.service;

import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.diet.exception.embedding.EmbeddingFailedException;
import org.sopt.carena.healthreport.application.port.in.SaveHealthReportEmbeddingUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.member.domain.Member;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveHealthReportEmbeddingService implements SaveHealthReportEmbeddingUseCase {
	private final EmbeddingPort embeddingPort;
	private final HealthReportEmbeddingPersistencePort healthReportEmbeddingPersistencePort;

	@Override
	@Retryable(
			retryFor = EmbeddingFailedException.class,
			maxAttempts = 3,
			backoff = @Backoff(delay = 5000, multiplier = 2),
			recover = "recoverEmbedding"
	)
	public void embeddingAndSave(final String embeddingText, final Member member, final HealthReport healthReport) {
		// 임베딩 호출
		float[] embedding = embeddingPort.embed(embeddingText).vector();

		// 임베딩 결과 도메인 생성
		HealthReportEmbedding healthReportEmbedding = HealthReportEmbedding.builder()
				.embeddingText(embeddingText)
				.embedding(embedding)
				.memberId(member.getId())
				.healthReportId(healthReport.getId())
				.build();

		// 임베딩 결과 저장
		healthReportEmbeddingPersistencePort.saveHealthReportEmbedding(healthReportEmbedding);
	}

	@Recover
	public void recoverEmbedding(
			final EmbeddingFailedException e,
			final String embeddingText,
			final Member member,
			final HealthReport healthReport
	) {
		log.error("건강검진 리포트 임베딩 실패: memberId={}, reportId={}, error={}", 
				member.getId(), healthReport.getId(), e.getMessage());
	}
}
