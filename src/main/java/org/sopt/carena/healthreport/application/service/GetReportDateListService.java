package org.sopt.carena.healthreport.application.service;

import org.sopt.carena.healthreport.application.dto.view.HealthReportDateListView;
import org.sopt.carena.healthreport.application.port.in.GetReportDateListUseCase;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetReportDateListService implements GetReportDateListUseCase {
	private final HealthReportPersistencePort healthReportPersistencePort;
	private final MemberPersistencePort memberPersistencePort;

	public HealthReportDateListView getReportDateList(final long memberId, final int index) {
		memberPersistencePort.getMemberById(memberId).orElseThrow(MemberNotFoundException::new);

		return HealthReportDateListView.from(
				healthReportPersistencePort.findAllByMemberIdOrderByHealthCheckDateDesc(memberId, index));
	}
}
