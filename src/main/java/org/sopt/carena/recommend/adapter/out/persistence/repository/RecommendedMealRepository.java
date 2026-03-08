package org.sopt.carena.recommend.adapter.out.persistence.repository;

import java.util.Optional;

import org.sopt.carena.recommend.adapter.out.persistence.entity.RecommendedMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecommendedMealRepository extends JpaRepository<RecommendedMealEntity, Long> {
	Optional<RecommendedMealEntity> findTopByMemberEntityIdOrderByCreatedAtDesc(long memberId);

	@Modifying
	@Query("DELETE FROM RecommendedMealEntity r WHERE r.memberEntity.id = :memberId")
	void deleteAllByMemberEntityId(Long memberId);
}
