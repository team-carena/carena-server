package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.view.DietListResultView;
import org.sopt.carena.diet.application.port.in.GetSimilarDietListUseCase;
import org.sopt.carena.diet.application.port.out.*;
import org.sopt.carena.diet.application.service.helper.AggregateAndPaginate;
import org.sopt.carena.diet.domain.value.DietChunkSimilarity;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.value.PagedDietSimilarity;
import org.sopt.carena.healthreport.application.port.out.HealthReportEmbeddingPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SimilarDietListService implements GetSimilarDietListUseCase {

    private final DietPersistencePort dietPersistencePort;
    private final HealthReportEmbeddingPersistencePort healthReportEmbeddingPersistencePort;
    private final LoadDietChunksByVectorPort loadDietChunksByVectorPort;
    private final AggregateAndPaginate aggregateAndPaginate;
    private static final int PAGE_SIZE = 10;

    @Override
    public DietListResultView getDietList(final int page, final long memberId) {
        //  최신 건강검진 조회
        HealthReport healthReport =
                healthReportEmbeddingPersistencePort.findLatestByMemberId(memberId);
        // 건강검진이 없는 경우 → 최신순 식단 반환
        if (healthReport == null) {
            log.info("건강검진 없음 - 최신 식단 목록 반환, memberId={}", memberId);
            return getLatestDietList(page);
        }
        // 임베딩 기반 추천
        return getDietListByEmbedding(page, memberId, healthReport);
    }

    private DietListResultView getLatestDietList(final int page) {

        Pageable pageable = PageRequest.of(
                page - 1,
                PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Slice<DietInformation> slice =
                dietPersistencePort.loadDietList(pageable);

        List<DietListResultView.DietItem> items = slice.getContent().stream()
                .map(diet -> new DietListResultView.DietItem(
                        diet.getId(),
                        diet.getTitle()
                ))
                .toList();

        return new DietListResultView(items, slice.hasNext());
    }

    private DietListResultView getDietListByEmbedding(
            final int page,
            final long memberId,
            final HealthReport healthReport
    ) {
        // 임베딩 조회
        HealthReportEmbedding embedding =
                healthReportEmbeddingPersistencePort
                        .findByHealthReportId(healthReport.getId());

        String embeddingText = embedding.getEmbeddingText();

        if (embeddingText == null || embeddingText.isEmpty()) {
            log.warn("임베딩 텍스트 없음 - 최신 식단으로 fallback");
            return getLatestDietList(page);
        }

        int topK = PAGE_SIZE * 10;

        List<DietChunkSimilarity> similarChunks =
                loadDietChunksByVectorPort.findSimilarChunks(embeddingText, topK);

        if (similarChunks.isEmpty()) {
            log.info("유사 식단 없음 - 최신 식단으로 fallback");
            return getLatestDietList(page);
        }

        int offset = (page - 1) * PAGE_SIZE;

        PagedDietSimilarity paged =
                aggregateAndPaginate.processAndPaginate(
                        similarChunks, offset, PAGE_SIZE
                );

        List<Long> dietIds = paged.content().stream()
                .map(DietChunkSimilarity::dietInformationId)
                .distinct()
                .toList();

        Map<Long, DietInformation> dietMap =
                dietPersistencePort.findAllByIds(dietIds);

        //이 과정 안으로 넣기 (정적메서드 사용)
        List<DietListResultView.DietItem> items = paged.content().stream()
                .map(chunk -> dietMap.get(chunk.dietInformationId()))
                .filter(Objects::nonNull)
                .map(diet -> new DietListResultView.DietItem(
                        diet.getId(),
                        diet.getTitle()
                ))
                .toList();

        return new DietListResultView(items, paged.hasNext());
    }
}