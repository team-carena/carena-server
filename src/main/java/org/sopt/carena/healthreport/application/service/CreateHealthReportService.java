package org.sopt.carena.healthreport.application.service;

import java.util.concurrent.ExecutorService;

import org.sopt.carena.healthreport.application.converter.HealthReportEmbeddingConverter;
import org.sopt.carena.healthreport.application.dto.command.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.in.SaveHealthReportEmbeddingUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportAlreadyExistsException;
import org.sopt.carena.member.application.port.in.HealthScoreUseCase;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;

import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.sopt.carena.recommend.application.port.in.CreateRecommendedMealUseCase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateHealthReportService implements CreateHealthReportUseCase {
	private final CreateRecommendedMealUseCase createRecommendedMealUseCase;
	private final HealthReportPersistencePort healthReportPersistencePort;
	private final MemberPersistencePort memberPersistencePort;
	private final HealthScoreUseCase healthScoreUseCase;
	private final SaveHealthReportEmbeddingUseCase saveHealthReportEmbeddingUseCase;
	private final ExecutorService virtualExecutorService;

	public void createHealthReport(final CreateHealthReportCommand command) {
		Member member = memberPersistencePort.getMemberById(command.memberId())
				.orElseThrow(MemberNotFoundException::new);

		if (healthReportPersistencePort.existsByMemberIdAndHealthCheckDate(
				command.memberId(),
				command.healthCheckDate()
		)) {
			throw new HealthReportAlreadyExistsException();
		}

		HealthReport healthReport = healthReportPersistencePort
				.saveHealthReport(HealthReport.create(command, member.getGender()));

		boolean isLatestReport = !healthReportPersistencePort
				.hasMoreRecentHealthReport(command.memberId(), command.healthCheckDate());

		if (isLatestReport) {
			String embeddingText = HealthReportEmbeddingConverter.toEmbeddingText(healthReport);

			healthScoreUseCase.updateMemberScore(member, healthReport);
			virtualExecutorService.submit(
					() -> saveHealthReportEmbeddingUseCase.embeddingAndSave(embeddingText, member, healthReport));
			createRecommendedMealUseCase.saveRagResult(member.getId());
		}
	}
}
