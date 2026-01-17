package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.CautionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CautionCategoryRepository extends JpaRepository<CautionCategoryEntity, Long> {
    @Query(value = "SELECT cautionary::text FROM caution_category WHERE diet_information_id = :dietId",
            nativeQuery = true)
    Optional<String> findCautionaryJsonByDietId(@Param("dietId") Long dietId);

    Optional<CautionCategoryEntity> findByDietInformationId(Long dietInformationId);
}