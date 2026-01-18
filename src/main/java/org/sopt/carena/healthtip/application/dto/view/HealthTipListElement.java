package org.sopt.carena.healthtip.application.dto.view;

import org.sopt.carena.healthtip.domain.HealthTip;

public record HealthTipListElement(
		Long id,
		String title
) {
	public static HealthTipListElement from(final HealthTip healthTip) {
		return new HealthTipListElement(healthTip.getId(), healthTip.getTitle());
	}
}
