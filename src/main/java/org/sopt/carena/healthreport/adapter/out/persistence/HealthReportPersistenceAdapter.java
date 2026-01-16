package org.sopt.carena.healthreport.adapter.out.persistence;

import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthReportPersistenceAdapter implements HealthReportPersistencePort {
	private final HealthReportRepository healthReportRepository;
	private final MemberJpaRepository memberJpaRepository;

	@Transactional
	public void saveHealthReport(final HealthReport healthReport) {
		MemberEntity memberEntityProxy = memberJpaRepository.getReferenceById(healthReport.getMemberId());

		healthReportRepository.save(HealthReportMapper.toEntity(healthReport, memberEntityProxy));
	}
}
