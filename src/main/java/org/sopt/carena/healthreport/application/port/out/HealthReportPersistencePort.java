package org.sopt.carena.healthreport.application.port.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.springframework.data.domain.Slice;

public interface HealthReportPersistencePort {
	HealthReport saveHealthReport(HealthReport healthReport);

	boolean existsByMemberIdAndHealthCheckDate(long memberId, LocalDate healthCheckDate);

	Slice<HealthReport> findAllByMemberIdOrderByHealthCheckDateDesc(long memberId, int index);

	Optional<HealthReport> findByMemberIdAndHealthReportId(long memberId, long healthReportId);

	List<HealthReport> findLatestHealthReportsHeightIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsWeightIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsWaistCircumferenceIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsBmiIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsSystolicBpIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsDiastolicBpIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsHemoglobinIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsFastingGlucoseIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsAstIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsAltIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsGammaGtpIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsSerumCreatinineIsNotNullByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsEgfrIsNotNullByMemberId(long memberId);
}
