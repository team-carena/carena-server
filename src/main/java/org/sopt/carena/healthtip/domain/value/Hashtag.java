package org.sopt.carena.healthtip.domain.value;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;

public record Hashtag(
		Long id,
		String name
) {
	public static Hashtag from(String name) {
		return new Hashtag(null, name);
	}

	public static Hashtag of(long id, String name) {
		return new Hashtag(id, name);
	}
}
