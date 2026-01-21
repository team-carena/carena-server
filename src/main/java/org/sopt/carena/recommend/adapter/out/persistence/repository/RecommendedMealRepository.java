package org.sopt.carena.recommend.adapter.out.persistence.repository;

import java.util.Optional;

import org.sopt.carena.recommend.adapter.out.persistence.entity.RecommendedMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedMealRepository extends JpaRepository<RecommendedMealEntity, Long> {
	Optional<RecommendedMealEntity> findTopByMemberEntityIdOrderByCreatedAtDesc(long memberId);
}
