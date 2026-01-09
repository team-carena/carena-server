package org.sopt.carena.healthtip.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HashtagEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipHashtagEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HashtagEntityRepository;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HealthTipEntityRepository;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HealthTipHashtagEntityRepository;
import org.sopt.carena.healthtip.application.dto.commend.CreateHealthTipCommend;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthTipPersistenceAdapter implements HealthTipPersistencePort {
	private final HealthTipEntityRepository healthTipEntityRepository;
	private final HashtagEntityRepository hashtagEntityRepository;
	private final HealthTipHashtagEntityRepository healthTipHashtagEntityRepository;

	public Slice<HealthTipEntity> getHealthTipList(final int page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		return healthTipEntityRepository.findAllByOrderByIdDesc(pageable);
	}

	public Optional<HealthTipEntity> getHealthTipDetail(final long id) {
		return healthTipEntityRepository.findByIdWithHashtags(id);
	}

	@Transactional
	public void saveHealthTipWithHashtags(final CreateHealthTipCommend commend) {
		// 해시태그 공백 및 중복 제거
		Set<String> tags = commend.hashtags().stream()
				.map(String::trim)
				.filter(name -> !name.isEmpty())
				.collect(Collectors.toSet());

		// 기존 해시태그 조회
		List<HashtagEntity> existingHashtags = hashtagEntityRepository.findByNameIn(commend.hashtags());

		// 이미 존재하는 해시태그 이름 추출
		Set<String> existingNames = existingHashtags.stream()
				.map(HashtagEntity::getName)
				.collect(Collectors.toSet());

		// 새로운 태그의 엔티티 생성
		List<HashtagEntity> newHashtags = tags.stream()
				.filter(name -> !existingNames.contains(name))
				.map(HashtagEntity::new)
				.toList();

		// 엔티티 저장
		if (!newHashtags.isEmpty()) {
			hashtagEntityRepository.saveAll(newHashtags);
		}

		List<HashtagEntity> allHashtags = new ArrayList<>();
		allHashtags.addAll(existingHashtags);
		allHashtags.addAll(newHashtags);

		// 건강팁 생성
		HealthTipEntity healthTip = healthTipEntityRepository.save(
				HealthTipEntity.builder()
						.title(commend.title())
						.subTitle(commend.subTitle())
						.content(commend.content())
						.reference(commend.reference())
						.build()
		);

		// 연관관계 생성
		List<HealthTipHashtagEntity> relations = allHashtags.stream()
				.map(tag -> new HealthTipHashtagEntity(healthTip, tag))
				.toList();

		healthTipHashtagEntityRepository.saveAll(relations);
	}

	@Transactional
	public void deleteHealthTip(long id) {
		healthTipHashtagEntityRepository.deleteByHealthTipId(id);
		healthTipEntityRepository.deleteById(id);
	}
}
