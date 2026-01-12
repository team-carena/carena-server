package org.sopt.carena.healthtip.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

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

	// 기존 해시태그 조회
	public List<Hashtag> getExistingHashtagsByNames(final List<Hashtag> hashtags) {
		List<String> tagNames = hashtags.stream()
				.map(Hashtag::name)
				.toList();

		return hashtagEntityRepository.findByNameIn(tagNames).stream()
				.map(entity -> Hashtag.of(entity.getId(), entity.getName()))
				.toList();
	}

	// 해시태그 저장
	@Transactional
	public List<Hashtag> saveHashtag(final List<Hashtag> hashtags) {
		List<HashtagEntity> hashtagEntities = hashtags.stream()
				.map(hashtag -> new HashtagEntity(hashtag.name()))
				.toList();

		if (!hashtagEntities.isEmpty()) {
			hashtagEntityRepository.saveAll(hashtagEntities);
		}

		return hashtagEntities.stream()
				.map(entity -> Hashtag.of(entity.getId(), entity.getName()))
				.toList();
	}

	@Transactional
	public void saveHealthTipWithHashtags(final HealthTip healthTip) {
		// 건강팁 생성
		HealthTipEntity healthTipEntity = healthTipEntityRepository.save(HealthTipMapper.toEntity(healthTip));

		// 프록시 객체 생성
		List<HashtagEntity> hashtagEntities = healthTip.getHashtags().stream()
				.map(hashtag -> hashtagEntityRepository.getReferenceById(hashtag.id()))
				.toList();

		// 연관관계 생성
		List<HealthTipHashtagEntity> relations = hashtagEntities.stream()
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
