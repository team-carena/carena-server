package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.in.CreateDietRecommendUseCase;
import org.sopt.carena.diet.application.port.out.LoadHealthEmbeddingPort;
import org.sopt.carena.diet.application.port.out.SaveDietRecommendPort;
import org.sopt.carena.diet.application.port.out.SearchSimilarDietPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CreateDietRecommendService implements CreateDietRecommendUseCase{
    private final SearchSimilarDietPort searchSimilarDietPort;
    private final SaveDietRecommendPort saveDietRecommendPort;
    private final LoadHealthEmbeddingPort loadHealthEmbeddingPort;


    private static final int MAX_RECOMMENDATIONS = 13;

    @Override
    @Transactional
    public void createRecommendation(Long memberId,
                                     Long healthReportId) {
        log.info("식단 추천 생성 시작 - memberId: {}, healthReportId: {}", memberId, healthReportId);

        HealthReportEmbedding healthEmbedding = loadHealthEmbeddingPort
                .findByHealthReportId(healthReportId)
                .orElseThrow(HealthReportNotFoundException::new);
        log.debug("건강검진 임베딩 조회 완료 - text: {}", healthEmbedding.getEmbeddingText());

        createRecommendationsByEmbedding(
                memberId,
                healthReportId,
                healthEmbedding.getEmbedding()
        );
    }
    /**
     * 직접 임베딩 벡터로 식단 추천 생성 및 저장
     */
    @Override
    @Transactional
    public List<DietRecommend> createRecommendationsByEmbedding(
            Long memberId,
            Long healthReportId,
            float[] healthEmbedding
    ) {
        log.info("임베딩 벡터로 식단 추천 생성 - memberId: {}, healthReportId: {}",
                memberId, healthReportId);

        List<DietRecommend> recommendations = searchSimilarDietPort.searchSimilarDiets(
                memberId,
                healthReportId,
                healthEmbedding,
                MAX_RECOMMENDATIONS
        );

        log.info("유사 식단 검색 완료 - 개수: {}", recommendations.size());

        //  저장 (Port 호출)
        List<DietRecommend> saved = saveDietRecommendPort.saveAll(recommendations);

        log.info("식단 추천 저장 완료 - memberId: {}, healthReportId: {}, 개수: {}",
                memberId, healthReportId, saved.size());

        return saved;
    }

}