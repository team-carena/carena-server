package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthReportRepository extends JpaRepository<HealthReportEntity, Long> {
}
