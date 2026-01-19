package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.out.LoadLatestHealthReportPort;
import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LatestHealthReportPersistenceAdapter implements LoadLatestHealthReportPort {

    private final HealthReportRepository healthReportRepository;
    @Override
    public HealthReport findLatestByMemberId(Long memberId) {

        HealthReport healthReport = healthReportRepository
                .findLatestByMemberId(memberId)
                .map(HealthReportMapper::toDomain)  // Entity → Domain 변환
                .orElseThrow(HealthReportNotFoundException::new);

        log.debug("최신 건강검진 조회 완료 - memberId: {}, healthReportId: {}",
                memberId, healthReport.getId());
        return healthReport;
    }
}