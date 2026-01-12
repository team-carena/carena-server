package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.Optional;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthTipEntityRepository extends JpaRepository<HealthTipEntity, Long> {
	Slice<HealthTipEntity> findAllByOrderByIdDesc(Pageable pageable);

	@Query("""
				select distinct ht
				from HealthTipEntity ht
				left join fetch ht.hashtags hth
				left join fetch hth.hashtag
				where ht.id = :id
			""")
	Optional<HealthTipEntity> findByIdWithHashtags(@Param("id") long id);
}
