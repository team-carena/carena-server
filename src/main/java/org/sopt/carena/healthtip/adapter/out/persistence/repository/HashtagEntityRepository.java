package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.List;
import java.util.Set;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagEntityRepository extends JpaRepository<HashtagEntity, Long> {
	List<HashtagEntity> findByNameIn(Set<String> names);
}
