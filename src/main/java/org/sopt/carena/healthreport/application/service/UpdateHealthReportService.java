package org.sopt.carena.healthreport.application.service;

import java.util.concurrent.ExecutorService;

import org.sopt.carena.healthreport.application.converter.HealthReportEmbeddingConverter;
import org.sopt.carena.healthreport.application.dto.command.UpdateHealthReportCommand;
import org.sopt.carena.healthreport.application.port.in.SaveHealthReportEmbeddingUseCase;
import org.sopt.carena.healthreport.application.port.in.UpdateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportAlreadyExistsException;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.sopt.carena.member.application.port.in.HealthScoreUseCase;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.sopt.carena.recommend.application.port.in.CreateRecommendedMealUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateHealthReportService implements UpdateHealthReportUseCase {
	private final CreateRecommendedMealUseCase createRecommendedMealUseCase;
	private final HealthReportPersistencePort healthReportPersistencePort;
	private final MemberPersistencePort memberPersistencePort;
	private final HealthScoreUseCase healthScoreUseCase;
	private final SaveHealthReportEmbeddingUseCase saveHealthReportEmbeddingUseCase;
	private final ExecutorService virtualExecutorService;

	@Override
	@Transactional
	public void updateHealthReport(final UpdateHealthReportCommand command) {
		Member member = memberPersistencePort.getMemberById(command.memberId())
				.orElseThrow(MemberNotFoundException::new);

		HealthReport healthReport = healthReportPersistencePort
				.findByMemberIdAndHealthReportId(command.memberId(), command.healthReportId())
				.orElseThrow(HealthReportNotFoundException::new);

		if (!healthReport.getHealthCheckDate().equals(command.healthCheckDate())
				&& healthReportPersistencePort.existsByMemberIdAndHealthCheckDate(
				command.memberId(),
				command.healthCheckDate()
		)) {
			throw new HealthReportAlreadyExistsException();
		}

		healthReport.update(command);
		healthReportPersistencePort.saveHealthReport(healthReport);

		String embeddingText = HealthReportEmbeddingConverter.toEmbeddingText(healthReport);

		// Update score
		healthScoreUseCase.updateMemberScore(member, healthReport);

		virtualExecutorService.submit(
				() -> saveHealthReportEmbeddingUseCase.embeddingAndSave(embeddingText, member, healthReport));
		createRecommendedMealUseCase.saveRagResult(member.getId());
	}
}
