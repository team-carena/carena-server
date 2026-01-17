package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DietInformationJpaRepository extends JpaRepository<DietInformationEntity, Long> {

    // 리스트 조회 (일단 최신순) : todo: 관련도 높은순으로 수정!
    Slice<DietInformationEntity> findAllByOrderByIdDesc(Pageable pageable);

    @Query("""
        SELECT DISTINCT d FROM DietInformationEntity d
        LEFT JOIN FETCH d.chunks
        WHERE d.id = :id
        """)
    Optional<DietInformationEntity> findByIdWithChunks(@Param(value = "id") Long id);


}