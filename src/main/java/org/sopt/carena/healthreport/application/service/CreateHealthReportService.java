package org.sopt.carena.healthreport.application.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.sopt.carena.diet.application.port.out.EmbeddingGenerator;
import org.sopt.carena.healthreport.application.dto.command.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;

import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateHealthReportService implements CreateHealthReportUseCase {
	private final HealthReportPersistencePort healthReportPersistencePort;
	private final MemberPersistencePort memberPersistencePort;
	private final HealthReportEmbeddingPersistencePort healthReportEmbeddingPersistencePort;
	private final EmbeddingGenerator embeddingGenerator;

	private final ExecutorService virtualExecutorService = Executors.newVirtualThreadPerTaskExecutor();

	public void createHealthReport(final CreateHealthReportCommand commend) {
		Member member = memberPersistencePort.getMemberById(commend.memberId())
				.orElseThrow(MemberNotFoundException::new);

		HealthReport healthReport = healthReportPersistencePort
				.saveHealthReport(HealthReport.create(commend, member.getGender()));

		virtualExecutorService.submit(() -> embeddingAndSave(member, healthReport));
	}

	private void embeddingAndSave(Member member, HealthReport healthReport) {
		String embeddingText = healthReport.getStatusCarriers().stream()
				.filter(carrier -> carrier.getRiskLevel() != RiskLevel.NONE)
				.map(HealthStatusCarrier::getDescription)
				.collect(Collectors.collectingAndThen(
						Collectors.joining(", "),
						s -> s.isEmpty() ? "정상" : s  //이거 문구 추가하기
				));

		// 임베딩 호출
		float[] embedding = embeddingGenerator.embed(embeddingText);

		// 임베딩 결과 엔티티 생성
		HealthReportEmbedding healthReportEmbedding = HealthReportEmbedding.builder()
				.embeddingText(embeddingText)
				.embedding(embedding) // 나중에 임베딩 결과 추가하기
				.memberId(member.getId())
				.healthReportId(healthReport.getId())
				.build();

		// 임베딩 결과 저장
		healthReportEmbeddingPersistencePort.saveHealthReportEmbedding(healthReportEmbedding);
	}
}
