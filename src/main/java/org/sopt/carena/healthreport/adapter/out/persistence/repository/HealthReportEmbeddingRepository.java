package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthReportEmbeddingRepository extends JpaRepository<HealthReportEmbeddingEntity, Long> {
	Optional<HealthReportEmbeddingEntity> findByHealthReportEntityId(long healthReportEntityId);
}
