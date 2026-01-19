package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.view.DietListResultView;
import org.sopt.carena.diet.application.port.in.GetSimilarDietListUseCase;
import org.sopt.carena.diet.application.port.out.*;
import org.sopt.carena.diet.application.service.helper.DietSimilarityDomainService;
import org.sopt.carena.diet.domain.DietChunkSimilarity;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.PagedDietSimilarity;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SimilarDietListService implements GetSimilarDietListUseCase {

    private final DietPersistencePort dietPersistencePort;
    private final LoadLatestHealthReportPort loadLatestHealthReportPort;
    private final LoadHealthReportEmbeddingPort loadHealthReportEmbeddingPort;
    private final LoadDietChunksByVectorPort loadDietChunksByVectorPort;
    private final DietSimilarityDomainService dietSimilarityDomainService;
    private static final int PAGE_SIZE = 10;

    @Override
    public DietListResultView getDietList(final int page, final long memberId) {
        try {
            // 회원의 최신 건강검진 조회
            HealthReport healthReport = loadLatestHealthReportPort.findLatestByMemberId(memberId);
            log.debug("최신 건강검진 조회 완료 - healthReportId: {}", healthReport.getId());

            //임베딩  조회
            HealthReportEmbedding embedding = loadHealthReportEmbeddingPort
                    .findByHealthReportId(healthReport.getId());
            String embeddingText = embedding.getEmbeddingText();

            if (embeddingText == null || embeddingText.isEmpty()) {
                log.warn("임베딩 텍스트가 없습니다 - healthReportId: {}", healthReport.getId());
                return new DietListResultView(List.of(), false);
            }

            // 벡터 유사도 검색 -> 몇개 가져와야함?
            int topK=10;
            List<DietChunkSimilarity> similarChunks = loadDietChunksByVectorPort
                    .findSimilarChunks(embeddingText, topK);

            if (similarChunks.isEmpty()) {
                log.info("유사한 식단을 찾을 수 없습니다 - memberId: {}", memberId);
                return new DietListResultView(List.of(), false);
            }

            int offset = (page - 1) * PAGE_SIZE;
            PagedDietSimilarity paged = dietSimilarityDomainService
                    .processAndPaginate(similarChunks, offset, PAGE_SIZE);

            // 식단 상세 정보 조회
            List<Long> dietIds = paged.content().stream()
                    .map(DietChunkSimilarity::dietInformationId)
                    .collect(Collectors.toList());

            Map<Long, DietInformation> dietMap = dietPersistencePort
                    .findAllByIds(dietIds);

            // View 변환
            List<DietListResultView.DietItem> items = paged.content().stream()
                    .map(chunk -> dietMap.get(chunk.dietInformationId()))
                    .filter(Objects::nonNull)
                    .map(diet -> new DietListResultView.DietItem(
                            diet.getId(),
                            diet.getTitle()
                    ))
                    .toList();

            log.info("식단 목록 조회 완료 - 결과: {} 개, hasNext: {}",
                    items.size(), paged.hasNext());

            return new DietListResultView(items, paged.hasNext());

        } catch (IllegalArgumentException e) {
            log.error("식단 목록 조회 실패 - memberId: {}, error: {}", memberId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("식단 목록 조회 중 예상치 못한 오류 - memberId: {}", memberId, e);
            throw new RuntimeException("식단 목록 조회에 실패했습니다", e);
        }
    }

}