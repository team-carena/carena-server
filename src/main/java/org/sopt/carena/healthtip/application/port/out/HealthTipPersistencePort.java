package org.sopt.carena.healthtip.application.port.out;

import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthtip.domain.HealthTip;
import org.sopt.carena.healthtip.domain.value.Hashtag;
import org.springframework.data.domain.Slice;

public interface HealthTipPersistencePort {
	Slice<HealthTip> getHealthTipList(int page);

	Optional<HealthTip> getHealthTipDetail(long id);

	List<Hashtag> getExistingHashtagsByNames(List<Hashtag> hashtags);

	void saveHealthTipWithHashtags(HealthTip healthTip);

	List<Hashtag> saveHashtag(List<Hashtag> hashtags);

	void deleteHealthTip(long id);
}
