package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.out.LoadHealthReportEmbeddingPort;
import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportEmbeddingMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportEmbeddingNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthReportEmbeddingAdapter implements LoadHealthReportEmbeddingPort {

    private final HealthReportEmbeddingRepository healthReportEmbeddingRepository;

    @Override
    public HealthReportEmbedding findByHealthReportId(Long healthReportId) {
        HealthReportEmbedding embedding = healthReportEmbeddingRepository
                .findByHealthReportId(healthReportId)
                .map(HealthReportEmbeddingMapper::toDomain)
                .orElseThrow(HealthReportEmbeddingNotFoundException::new);

        log.debug("건강검진 임베딩 조회 완료 - healthReportId: {}", healthReportId);
        return embedding;
    }
}