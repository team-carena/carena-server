package org.sopt.carena.healthreport.adapter.out.persistence;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportEmbeddingMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthReportEmbeddingPersistenceAdapter implements HealthReportEmbeddingPersistencePort {
	private final HealthReportEmbeddingRepository healthReportEmbeddingRepository;

	private final HealthReportRepository healthReportRepository;
	private final MemberJpaRepository memberJpaRepository;

	@Transactional
	public void saveHealthReportEmbedding(HealthReportEmbedding healthReportEmbedding) {
		MemberEntity memberEntityProxy = memberJpaRepository.getReferenceById(healthReportEmbedding.getMemberId());
		HealthReportEntity healthReportEntityProxy = healthReportRepository.getReferenceById(healthReportEmbedding.getHealthReportId());

		HealthReportEmbeddingEntity healthReportEmbeddingEntity = HealthReportEmbeddingMapper
				.toEntity(healthReportEmbedding, memberEntityProxy, healthReportEntityProxy);

		healthReportEmbeddingRepository.save(healthReportEmbeddingEntity);
	}
}
