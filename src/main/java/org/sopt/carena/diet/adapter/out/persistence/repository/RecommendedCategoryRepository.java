package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.RecommendedCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecommendedCategoryRepository extends JpaRepository<RecommendedCategoryEntity, Long> {
    Optional<RecommendedCategoryEntity> findByDietInformationId(Long dietInformationId);
    @Query(value = "SELECT categories::text FROM recommended_category WHERE diet_information_id = :dietId",
            nativeQuery = true)
    Optional<String> findCategoriesJsonByDietId(@Param("dietId") Long dietId);
}