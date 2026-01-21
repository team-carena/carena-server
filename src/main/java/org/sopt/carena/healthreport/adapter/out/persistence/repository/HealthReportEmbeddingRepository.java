package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthReportEmbeddingRepository extends JpaRepository<HealthReportEmbeddingEntity, Long> {
	@Query("SELECT h FROM HealthReportEmbeddingEntity h WHERE h.healthReportEntity.id = :healthReportId")
	Optional<HealthReportEmbeddingEntity> findByHealthReportId(@Param("healthReportId") Long healthReportId);
}
