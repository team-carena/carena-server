package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthReportRepository extends JpaRepository<HealthReportEntity, Long> {

	Optional<HealthReportEntity> findByMemberEntityIdAndId(long memberId, long healthReportId);

	Slice<HealthReportEntity> findAllByMemberEntityIdOrderByHealthCheckDateDesc(long memberId, Pageable pageable);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHeightIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWeightIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWaistCircumferenceIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndBmiIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSystolicBpIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndDiastolicBpIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHemoglobinIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndFastingGlucoseIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAstIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAltIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndGammaGtpIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSerumCreatinineIsNotNullOrderByHealthCheckDateDesc(long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndEgfrIsNotNullOrderByHealthCheckDateDesc(long memberId);
}
