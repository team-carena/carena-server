package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.SearchSimilarDietPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.sopt.carena.diet.exception.diet.RecommendationNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchSimilarDietAdapter implements SearchSimilarDietPort {

    private final DietInformationJpaRepository dietRepository;

    @Override
    public List<DietRecommend> searchSimilarDiets(
            Long memberId,
            Long healthReportId,
            float[] healthEmbedding,
            int limit
    ) {
        // 벡터를 PostgreSQL 포맷으로 변환
        String vectorString = convertToVectorString(healthEmbedding);

        // 벡터 유사도 검색
        List<Object[]> results = dietRepository
                .findRecommendedByHealthEmbeddingWithScore(vectorString, limit);

        if (results.isEmpty()) {
            log.warn("추천 가능한 식단이 없습니다 - memberId: {}", memberId);
            throw new RecommendationNotFoundException();
        }

        log.debug("벡터 검색 완료 - 검색된 개수: {}", results.size());

        //  쿼리 결과 → 도메인 변환
        return results.stream()
                .map(row -> convertToDomain(row, memberId, healthReportId))
                .toList();
    }

    /**
     * 쿼리 결과를 도메인으로 변환 (Adapter의 책임)
     */
    private DietRecommend convertToDomain(Object[] row, Long memberId, Long healthReportId) {
        DietInformationEntity diet = (DietInformationEntity) row[0];
        Double distance = ((Number) row[1]).doubleValue();
        Double similarity = 1.0 - distance; // 코사인 거리 → 유사도

        String content = diet.getContent();

        log.debug("식단 변환 - title: {}, score: {:.2f}%", diet.getTitle(), similarity * 100);

        return DietRecommend.create(
                memberId,
                healthReportId,
                diet.getId(),
                diet.getTitle(),
                content,
                similarity
        );
    }

    /**
     * float[] 배열을 PostgreSQL vector 포맷으로 변환
     */
    private String convertToVectorString(float[] embedding) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(embedding[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}