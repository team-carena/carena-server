package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.port.out.LoadHealthEmbeddingPort;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoadHealthEmbeddingAdapter implements LoadHealthEmbeddingPort {

    private final HealthReportEmbeddingRepository repository;

    @Override
    public Optional<HealthEmbeddingData> findByHealthReportId(Long healthReportId) {
        return repository.findByHealthReportEntityId(healthReportId)
                .map(this::convertToVO);
    }

    /**
     * Entity → VO 변환
     */
    private HealthEmbeddingData convertToVO(HealthReportEmbeddingEntity entity) {
        return new HealthEmbeddingData(
                entity.getId(),
                entity.getEmbeddingText(),
                entity.getEmbedding()
        );
    }
}