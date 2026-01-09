package org.sopt.carena.healthtip.adapter.out.persistence.mapper;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.sopt.carena.healthtip.domain.HealthTip;
import org.sopt.carena.healthtip.domain.value.Hashtag;

public class HealthTipMapper {
	public static HealthTip toDomain(HealthTipEntity entity) {
		return HealthTip.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.subTitle(entity.getSubTitle())
				.content(entity.getContent())
				.reference(entity.getReference())
				.hashtags(entity.getHashtags().stream()
						.map(healthTipHashtagEntity ->
								Hashtag.of(healthTipHashtagEntity.getHashtag().getId(),
										healthTipHashtagEntity.getHashtag().getName()))
						.toList())
				.build();
	}

	public static HealthTip toDomainWithoutHashtags(HealthTipEntity entity) {
		return HealthTip.builder()
				.id(entity.getId())
				.title(entity.getTitle())
				.subTitle(entity.getSubTitle())
				.content(entity.getContent())
				.reference(entity.getReference())
				.build();
	}

	public static HealthTipEntity toEntity(HealthTip domain) {
		return HealthTipEntity.builder()
				.title(domain.getTitle())
				.subTitle(domain.getSubTitle())
				.content(domain.getContent())
				.reference(domain.getReference())
				.build();
	}
}
