package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DietChunkJpaRepository extends JpaRepository<DietChunkEntity, Long> {
    /**
     * pgvector 코사인 유사도 기반 식단 검색
     * -  각 diet_information별 최고 유사도 청크만 선택
     * - 유사도 높은 순 정렬
     * - 페이징 적용
     */
    //todo: low number에서 fullscan발생하니 고치기
    @Query(value = """
        WITH ranked_chunks AS (
            SELECT 
                dc.diet_information_id,
                dc.section,
                (1.0 - (dc.embedding <=> CAST(:queryVector AS vector))) AS similarity,
                ROW_NUMBER() OVER (
                    PARTITION BY dc.diet_information_id 
                    ORDER BY (dc.embedding <=> CAST(:queryVector AS vector)) ASC
                ) as rn
            FROM diet_chunk dc
            WHERE dc.embedding IS NOT NULL
        )
        SELECT 
            diet_information_id,
            section,
            similarity
        FROM ranked_chunks
        WHERE rn = 1
        ORDER BY similarity DESC
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<DietVectorSearchResult> findSimilarDietsByVector(
            @Param("queryVector") String queryVector,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
