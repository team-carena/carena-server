package org.sopt.carena.healthtip.application.dto.view;

import org.sopt.carena.healthtip.domain.HealthTip;

public record HealthTipListElement(
		String id,
		String title
) {
	public static HealthTipListElement from(final HealthTip healthTip) {
		return new HealthTipListElement(String.valueOf(healthTip.getId()), healthTip.getTitle());
	}
}
