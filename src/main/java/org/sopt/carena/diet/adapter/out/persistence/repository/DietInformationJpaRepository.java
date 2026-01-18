package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DietInformationJpaRepository extends JpaRepository<DietInformationEntity, Long> {

    // 리스트 조회 (일단 최신순) : todo: 관련도 높은순으로 수정!
    @Query("""
        SELECT DISTINCT d FROM DietInformationEntity d
        LEFT JOIN FETCH d.chunks
        LEFT JOIN FETCH d.recommendedFood
        LEFT JOIN FETCH d.cautionaryFood
        ORDER BY d.id DESC
        """)
    Slice<DietInformationEntity> findAllByOrderByIdDesc(Pageable pageable);

    @Query("""
        SELECT DISTINCT d FROM DietInformationEntity d
        LEFT JOIN FETCH d.chunks
        WHERE d.id = :id
        """)
    Optional<DietInformationEntity> findByIdWithChunks(@Param(value = "id") Long id);

    // 건강검진 기반 추천 (코사인 유사도)
    @Query(value = """
        SELECT DISTINCT d.*,
               MIN(c.embedding <=> CAST(:healthEmbedding AS vector)) as similarity_score
        FROM diet_information d
        INNER JOIN chunk c ON c.diet_information_id = d.id
        WHERE c.embedding IS NOT NULL
        GROUP BY d.id
        ORDER BY similarity_score ASC
        LIMIT :limit OFFSET :offset
        """,
            nativeQuery = true)
    List<DietInformationEntity> findRecommendedByHealthEmbedding(
            @Param("healthEmbedding") String healthEmbedding,
            @Param("limit") int limit,
            @Param("offset") long offset
    );


    /**
     * 건강검진 기반 유사한 식단 N개 조회 (유사도 점수 포함)
     */
    @Query(value = """
        SELECT d.*, MIN(c.embedding <=> CAST(:healthEmbedding AS vector)) as distance
        FROM diet_information d
        INNER JOIN chunk c ON c.diet_information_id = d.id
        WHERE c.embedding IS NOT NULL
        GROUP BY d.id
        ORDER BY distance ASC
        LIMIT :limit
        """,
            nativeQuery = true)
    List<Object[]> findRecommendedByHealthEmbeddingWithScore(
            @Param("healthEmbedding") String healthEmbedding,
            @Param("limit") int limit
    );


}