package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthTipEntityRepository extends JpaRepository<HealthTipEntity, Long>, HealthTipSearchRepository{
	Slice<HealthTipEntity> findAllByOrderByIdDesc(Pageable pageable);

	@Query("""
				select distinct ht
				from HealthTipEntity ht
				left join fetch ht.hashtags hth
				left join fetch hth.hashtag
				where ht.id = :id
			""")
	Optional<HealthTipEntity> findByIdWithHashtags(@Param(value = "id") long id);

	@Query(nativeQuery = true, value = "select * from health_tip order by random() limit :limit")
	List<HealthTipEntity> findRandomHealthTipEntities(@Param(value = "limit") int limit);
}
