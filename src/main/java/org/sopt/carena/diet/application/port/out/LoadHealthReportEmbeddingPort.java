package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.healthreport.domain.HealthReportEmbedding;

/**
 * 건강검진 임베딩 조회 포트
 */
public interface LoadHealthReportEmbeddingPort {
    HealthReportEmbedding findByHealthReportId(Long healthReportId);
}
