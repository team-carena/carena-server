package org.sopt.carena.healthreport.adapter.out.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthReportRepository extends JpaRepository<HealthReportEntity, Long> {

	Optional<HealthReportEntity> findByMemberEntityIdAndId(long memberId, long healthReportId);

	@Query("""
        SELECT h FROM HealthReportEntity h
        WHERE h.memberEntity.id = :memberId
        ORDER BY h.healthCheckDate DESC
        LIMIT 1
        """)
	Optional<HealthReportEntity> findLatestByMemberId(@Param("memberId") Long memberId);

	boolean existsByMemberEntityIdAndHealthCheckDate(Long memberId, LocalDate healthCheckDate);

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
