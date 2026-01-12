package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthTipHashtagEntityRepository extends JpaRepository<HealthTipHashtagEntity, Long> {
	void deleteByHealthTipId(long id);
}
