package org.sopt.carena.healthtip.application.port.out;

import java.util.Optional;

import org.sopt.carena.healthtip.domain.HealthTip;
import org.springframework.data.domain.Slice;

public interface HealthTipPersistencePort {
	Slice<HealthTip> getHealthTipList(int page);

	Optional<HealthTip> getHealthTipDetail(long id);

	void saveHealthTipWithHashtags(HealthTip healthTip);

	void deleteHealthTip(long id);
}
