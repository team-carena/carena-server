package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DietPersistencePort {
    //Information만 저장하고 ID 반환
    Long saveInformation(DietInformation info);

    // 청크 + 카테고리 한 번에 저장
    void saveChunksAndCategories(
            Long documentId,
            List<DietChunk> chunks,
            Map<String, List<String>> recommends,
            List<String> cautionary
    );
    Optional<DietInformation> loadById(Long dietId);
    Slice<DietInformation> loadDietList(Pageable pageable); //건강검진 결과 없는 경우

    //벡터 유사도 기반 식단 목록 조회
    Slice<DietInformation> loadDietsByVectorSimilarity(
            float[] embeddingVector,
            int page,
            int pageSize
    );
}