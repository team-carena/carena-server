package org.sopt.carena.healthreport.application.service;

import org.sopt.carena.healthreport.application.dto.view.EntireHealthReportView;
import org.sopt.carena.healthreport.application.port.in.GetEntireHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetEntireHealthReportService implements GetEntireHealthReportUseCase {
	private final HealthReportPersistencePort healthReportPersistencePort;

	public EntireHealthReportView getEntireHealthReport(final long memberId, final long healthReportId) {
		return EntireHealthReportView.from(
				healthReportPersistencePort.findByMemberIdAndHealthReportId(memberId, healthReportId)
						.orElseThrow(HealthReportNotFoundException::new));
	}

}
