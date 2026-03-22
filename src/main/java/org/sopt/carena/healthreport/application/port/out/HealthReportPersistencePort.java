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

	Optional<HealthReport> findLatestHealthReportByMemberId(long memberId);

	List<HealthReport> findLatestHealthReportsHeightIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsWeightIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsWaistCircumferenceIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsBmiIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsSystolicBpIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsDiastolicBpIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsHemoglobinIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsFastingGlucoseIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsAstIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsAltIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsGammaGtpIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsSerumCreatinineIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);

	List<HealthReport> findLatestHealthReportsEgfrIsNotNullByMemberId(long memberId, LocalDate healthCheckDate);
}
