package org.sopt.carena.healthtip.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.sopt.carena.healthtip.application.dto.command.CreateHealthTipCommand;
import org.sopt.carena.healthtip.application.port.in.CreateHealthTipUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.sopt.carena.healthtip.domain.HealthTip;
import org.sopt.carena.healthtip.domain.value.Hashtag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateHealthTipService implements CreateHealthTipUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;

	public void createHealthTip(final CreateHealthTipCommand command) {
		HealthTip healthTip = HealthTip.create(command);

		// 기존 해시태그 조회
		List<Hashtag> existingHashtags = healthTipPersistencePort.getExistingHashtagsByNames(healthTip.getHashtags());

		// 기존 해시태그 이름 추출
		Set<String> existingHashtagNames = existingHashtags.stream()
				.map(Hashtag::name)
				.collect(Collectors.toSet());

		// 새로운 해시태그 추출
		List<Hashtag> newHashtags = healthTip.getHashtags().stream()
				.map(Hashtag::name)
				.filter(name -> !existingHashtagNames.contains(name))
				.map(Hashtag::from)
				.toList();

		// 새로운 해시태그 저장 후 vo에 id가 추가된 내용 적용
		newHashtags = healthTipPersistencePort.saveHashtag(newHashtags);

		List<Hashtag> hashtags = new ArrayList<>();
		hashtags.addAll(existingHashtags);
		hashtags.addAll(newHashtags);

		healthTip.updateHashtags(hashtags);

		// 건강팁과 해시태그 연관관계 엔티티 생성
		healthTipPersistencePort.saveHealthTipWithHashtags(healthTip);
	}
}
