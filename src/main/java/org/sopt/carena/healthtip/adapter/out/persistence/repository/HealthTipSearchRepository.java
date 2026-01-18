package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.List;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface HealthTipSearchRepository {
	Slice<HealthTipEntity> findHealthTipListWithHashTags(String hashtagName,Pageable pageable);

	List<HealthTipEntity> findRandomHealthTipsWithHashtags(String hashtagName, int limit);
}
