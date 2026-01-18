package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.healthreport.domain.HealthReportEmbedding;

import java.util.Optional;

public interface LoadHealthEmbeddingPort {

    Optional<HealthReportEmbedding> findByHealthReportId(Long healthReportId);

    /**
     * 건강검진 임베딩 데이터 (VO)
     */
    record HealthEmbeddingData(
            Long id,
            String embeddingText,
            float[] embedding
    ) {}
}