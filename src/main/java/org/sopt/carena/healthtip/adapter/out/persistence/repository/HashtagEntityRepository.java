package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.List;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagEntityRepository extends JpaRepository<HashtagEntity, Long> {
	List<HashtagEntity> findByNameIn(List<String> names);
}
