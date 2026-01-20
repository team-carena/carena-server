package org.sopt.carena.healthreport.application.service;

import java.util.concurrent.ExecutorService;

import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.healthreport.application.converter.HealthReportEmbeddingConverter;
import org.sopt.carena.healthreport.application.dto.command.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportAlreadyExistsException;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;

import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.sopt.carena.recommend.application.port.in.CreateRecommendedMealUseCase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateHealthReportService implements CreateHealthReportUseCase {
	private final CreateRecommendedMealUseCase createRecommendedMealUseCase;
	private final HealthReportPersistencePort healthReportPersistencePort;
	private final MemberPersistencePort memberPersistencePort;
	private final HealthReportEmbeddingPersistencePort healthReportEmbeddingPersistencePort;
	private final EmbeddingPort embeddingPort;
	private final ExecutorService virtualExecutorService;

	public void createHealthReport(final CreateHealthReportCommand commend) {
		Member member = memberPersistencePort.getMemberById(commend.memberId())
				.orElseThrow(MemberNotFoundException::new);

		if (healthReportPersistencePort.existsByMemberIdAndHealthCheckDate(
				commend.memberId(),
				commend.healthCheckDate()
		)) {
			throw new HealthReportAlreadyExistsException();
		}

		HealthReport healthReport = healthReportPersistencePort
				.saveHealthReport(HealthReport.create(commend, member.getGender()));

		String embeddingText = HealthReportEmbeddingConverter.toEmbeddingText(healthReport);

		virtualExecutorService.submit(() -> embeddingAndSave(embeddingText, member, healthReport));
		virtualExecutorService.submit(() -> createRecommendedMealUseCase.saveRagResult(member.getId(), healthReport.getId()));
	}

	private void embeddingAndSave(final String embeddingText, final Member member, final HealthReport healthReport) {
		// 임베딩 호출
		float[] embedding = embeddingPort.embed(embeddingText).vector();

		// 임베딩 결과 도매인 생성
		HealthReportEmbedding healthReportEmbedding = HealthReportEmbedding.builder()
				.embeddingText(embeddingText)
				.embedding(embedding)
				.memberId(member.getId())
				.healthReportId(healthReport.getId())
				.build();

		// 임베딩 결과 저장
		healthReportEmbeddingPersistencePort.saveHealthReportEmbedding(healthReportEmbedding);
	}
}
