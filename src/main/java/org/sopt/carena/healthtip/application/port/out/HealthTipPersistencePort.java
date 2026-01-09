package org.sopt.carena.healthtip.application.port.out;

import java.util.Optional;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.sopt.carena.healthtip.application.dto.commend.CreateHealthTipCommand;
import org.springframework.data.domain.Slice;

public interface HealthTipPersistencePort {
	Slice<HealthTipEntity> getHealthTipList(int page);

	Optional<HealthTipEntity> getHealthTipDetail(long id);

	void saveHealthTipWithHashtags(CreateHealthTipCommand commend);

	void deleteHealthTip(long id);
}
