package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DietChunkJpaRepository extends JpaRepository<DietChunkEntity, Long> {

    /**
     * pgvector의 코사인 거리(<=>)를 이용한 유사도 검색
     * 1.0 - distance = similarity
     */
    /*
    @Query(value = """
        SELECT 
            dc.id,
            dc.diet_information_id,
            dc.section,
            dc.content,
            dc.chunk_order,
            dc.created_at,
            (1.0 - (dc.embedding <=> CAST(:queryVector AS vector))) AS similarity
        FROM diet_chunk dc
        WHERE dc.embedding IS NOT NULL
        ORDER BY dc.embedding <=> CAST(:queryVector AS vector)
        LIMIT :topK
        """, nativeQuery = true)
    List<Object[]> findSimilarChunksByVector(
            @Param("queryVector") String queryVector,
            @Param("topK") int topK
    );

     */
    /**
     * pgvector 코사인 유사도 기반 식단 검색
     * - 윈도우 함수로 각 diet_information별 최고 유사도 청크만 선택
     * - 유사도 높은 순 정렬
     * - 페이징 적용
     */
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
    List<Object[]> findSimilarDietsByVector(
            @Param("queryVector") String queryVector,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    /**
     * 전체 결과 개수 확인 (hasNext 판단용)
     */
    @Query(value = """
        WITH ranked_chunks AS (
            SELECT 
                dc.diet_information_id,
                ROW_NUMBER() OVER (
                    PARTITION BY dc.diet_information_id 
                    ORDER BY (dc.embedding <=> CAST(:queryVector AS vector)) ASC
                ) as rn
            FROM diet_chunk dc
            WHERE dc.embedding IS NOT NULL
        )
        SELECT COUNT(*)
        FROM ranked_chunks
        WHERE rn = 1
        """, nativeQuery = true)
    Long countSimilarDietsByVector(@Param("queryVector") String queryVector);
}
