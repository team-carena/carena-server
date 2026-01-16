package org.sopt.carena.healthreport.application.service;

import java.util.stream.Collectors;

import org.sopt.carena.healthreport.application.dto.commend.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
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

	public void createHealthReport(final CreateHealthReportCommand commend) {
		Member member = memberPersistencePort.getMemberById(commend.memberId())
				.orElseThrow(MemberNotFoundException::new);

		HealthReport healthReport = HealthReport.create(commend, member.getGender());

		healthReportPersistencePort.saveHealthReport(healthReport);

		String summary = healthReport.getStatusCarriers().stream()
				.filter(carrier -> carrier.getRiskLevel() != RiskLevel.NONE)
				.map(HealthStatusCarrier::getDescription)
				.collect(Collectors.collectingAndThen(
						Collectors.joining(", "),
						s -> s.isEmpty() ? "정상" : s
				));

	}
}
