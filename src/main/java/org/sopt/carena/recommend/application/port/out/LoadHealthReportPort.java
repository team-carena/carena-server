package org.sopt.carena.recommend.application.port.out;

import java.util.Optional;

import org.sopt.carena.healthreport.domain.HealthReport;

public interface LoadHealthReportPort {
	Optional<HealthReport> findByMemberIdAndHealthReportId(long memberId, long healthReportId);
}
