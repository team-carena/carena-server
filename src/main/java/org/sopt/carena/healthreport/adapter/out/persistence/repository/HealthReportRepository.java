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

	Optional<HealthReportEntity> findTopByMemberEntityIdOrderByHealthCheckDateDesc(long memberId);

	@Query("""
			SELECT h FROM HealthReportEntity h
        WHERE h.memberEntity.id = :memberId
        ORDER BY h.healthCheckDate DESC
        LIMIT 1
        """)
	Optional<HealthReportEntity> findLatestByMemberId(@Param("memberId") Long memberId);

	boolean existsByMemberEntityIdAndHealthCheckDate(Long memberId, LocalDate healthCheckDate);

	Slice<HealthReportEntity> findAllByMemberEntityIdOrderByHealthCheckDateDesc(long memberId, Pageable pageable);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHeightIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWeightIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndWaistCircumferenceIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndBmiIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSystolicBpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndDiastolicBpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndHemoglobinIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndFastingGlucoseIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAstIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndAltIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndGammaGtpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndSerumCreatinineIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);

	List<HealthReportEntity> findTop5ByMemberEntityIdAndEgfrIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(long memberId, LocalDate healthCheckDate);
	}

