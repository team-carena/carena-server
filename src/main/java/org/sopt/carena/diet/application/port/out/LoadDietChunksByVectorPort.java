package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietChunkSimilarity;

import java.util.List;

/**
 * 벡터 유사도 기반 식단 청크 조회 포트
 */
public interface LoadDietChunksByVectorPort {
    List<DietChunkSimilarity> findSimilarChunks(
            String embeddingText,
            int topK
    );
}