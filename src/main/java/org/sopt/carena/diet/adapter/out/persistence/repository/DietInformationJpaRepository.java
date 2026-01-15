package org.sopt.carena.diet.adapter.out.persistence.repository;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietInformationJpaRepository extends JpaRepository<DietInformationEntity, Long> {
}