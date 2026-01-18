package org.sopt.carena.diet.application.port.out;


import org.sopt.carena.diet.domain.DietRecommend;

import java.util.List;

/**
 * 벡터 유사도 기반 식단 검색 포트
 */
public interface SearchSimilarDietPort {

    /**
     * 건강검진 임베딩과 유사한 식단 추천 생성
     *
     * @param memberId 회원 ID
     * @param healthReportId 건강검진 ID
     * @param healthEmbedding 건강검진 임베딩 벡터
     * @param limit 최대 추천 개수
     * @return 유사도 순으로 정렬된 식단 추천 리스트
     */
    List<DietRecommend> searchSimilarDiets(
            Long memberId,
            Long healthReportId,
            float[] healthEmbedding,
            int limit
    );
}
