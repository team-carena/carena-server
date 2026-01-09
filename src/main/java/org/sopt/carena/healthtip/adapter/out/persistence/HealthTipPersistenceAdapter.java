package org.sopt.carena.healthtip.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HashtagEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipHashtagEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.mapper.HealthTipMapper;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HashtagEntityRepository;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HealthTipEntityRepository;
import org.sopt.carena.healthtip.adapter.out.persistence.repository.HealthTipHashtagEntityRepository;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.sopt.carena.healthtip.domain.HealthTip;
import org.sopt.carena.healthtip.domain.value.Hashtag;
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

	public Slice<HealthTip> getHealthTipList(final int page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		return healthTipEntityRepository.findAllByOrderByIdDesc(pageable)
				.map(HealthTipMapper::toDomainWithoutHashtags);
	}

	public Optional<HealthTip> getHealthTipDetail(final long id) {
		return healthTipEntityRepository.findByIdWithHashtags(id)
				.map(HealthTipMapper::toDomain);
	}

	@Transactional
	public void saveHealthTipWithHashtags(final HealthTip healthTip) {

		// 기존 해시태그 조회
		List<HashtagEntity> existingHashtags = hashtagEntityRepository.findByNameIn(
				healthTip.getHashtags().stream().map(Hashtag::name).toList());

		// 이미 존재하는 해시태그 이름 추출
		Set<String> existingNames = existingHashtags.stream()
				.map(HashtagEntity::getName)
				.collect(Collectors.toSet());

		// 새로운 태그의 엔티티 생성
		List<HashtagEntity> newHashtags = healthTip.getHashtags().stream()
				.map(Hashtag::name)
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
		HealthTipEntity healthTipEntity = healthTipEntityRepository.save(HealthTipMapper.toEntity(healthTip));

		// 연관관계 생성
		List<HealthTipHashtagEntity> relations = allHashtags.stream()
				.map(tag -> new HealthTipHashtagEntity(healthTipEntity, tag))
				.toList();

		healthTipHashtagEntityRepository.saveAll(relations);
	}

	@Transactional
	public void deleteHealthTip(long id) {
		healthTipHashtagEntityRepository.deleteByHealthTipId(id);
		healthTipEntityRepository.deleteById(id);
	}
}
