package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthReportRepository extends JpaRepository<HealthReportEntity, Long> {

	Optional<HealthReportEntity> findByMemberEntityIdAndId(long memberId, long healthReportId);

	List<HealthReportEntity> findAllByMemberEntity_Id(long memberId);

	Slice<HealthReportEntity> findAllByMemberEntityIdOrderByHealthCheckDateDesc(long memberId, Pageable pageable);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHeightIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWeightIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWaistCircumferenceIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndBmiIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSystolicBpIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndDiastolicBpIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHemoglobinIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndFastingGlucoseIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAstIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAltIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndGammaGtpIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSerumCreatinineIsNotNullOrderByHealthCheckDateDesc(Long memberId);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndEgfrIsNotNullOrderByHealthCheckDateDesc(Long memberId);
}
