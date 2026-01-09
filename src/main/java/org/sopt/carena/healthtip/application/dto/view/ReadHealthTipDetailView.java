package org.sopt.carena.healthtip.application.dto.view;

import java.util.List;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;

public record ReadHealthTipDetailView(
		long id,
		String title,
		String subTitle,
		String content,
		String reference,
		List<String> hashtags
) {
	public static ReadHealthTipDetailView from(final HealthTipEntity healthTipEntity) {
		return new ReadHealthTipDetailView(
				healthTipEntity.getId(),
				healthTipEntity.getTitle(),
				healthTipEntity.getSubTitle(),
				healthTipEntity.getContent(),
				healthTipEntity.getReference(),
				healthTipEntity.getHashtags().stream()
						.map(healthTipHashtagEntity -> {
							return healthTipHashtagEntity.getHashtag().getName();
						}).toList()
		);
	}
}
