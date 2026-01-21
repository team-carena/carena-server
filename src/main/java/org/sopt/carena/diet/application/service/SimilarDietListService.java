package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.view.DietListResultView;
import org.sopt.carena.diet.application.port.in.GetSimilarDietListUseCase;
import org.sopt.carena.diet.application.port.out.*;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SimilarDietListService implements GetSimilarDietListUseCase {

    private final DietPersistencePort dietPersistencePort;
    private final HealthReportEmbeddingPersistencePort healthReportEmbeddingPersistencePort;
    private static final int PAGE_SIZE = 10;

    @Override
    public DietListResultView getDietList(final int page, final long memberId) {
        //  최신 건강검진 조회
        HealthReport healthReport = healthReportEmbeddingPersistencePort.findLatestByMemberId(memberId);
        // 건강검진이 없는 경우 → 최신순 식단 반환
        if (healthReport == null) {
            log.info("건강검진 없음 - 최신 식단 목록 반환, memberId={}", memberId);
            return getLatestDietList(page);
        }
        HealthReportEmbedding embedding = healthReportEmbeddingPersistencePort.findByHealthReportId(healthReport.getId());
        // 임베딩 기반 추천
        return getDietListByEmbedding(page, memberId, embedding);
    }

    private DietListResultView getLatestDietList(final int page) {

        Pageable pageable = PageRequest.of(
                page - 1,
                PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Slice<DietInformation> slice =
                dietPersistencePort.loadDietList(pageable);

        return DietListResultView.from(slice.getContent(), slice.hasNext());
    }

    private DietListResultView getDietListByEmbedding(
            final int page,
            final long memberId,
            final HealthReportEmbedding embedding
    ) {
        try {
            Slice<DietInformation> slice = dietPersistencePort.loadDietsByVectorSimilarity(
                    embedding.getEmbedding(),
                    page,
                    PAGE_SIZE
            );
            log.info("추천 식단 조회 완료 - memberId: {}, page: {}, 결과: {}개",
                    memberId, page, slice.getNumberOfElements());
            return DietListResultView.from(slice.getContent(), slice.hasNext());
        } catch (Exception e) {
            log.warn("추천 식단 조회 실패 - 최신 식단으로 fallback, memberId: {}", memberId, e);
            return getLatestDietList(page);
        }
    }
}