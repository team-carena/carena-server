package org.sopt.carena.healthtip.application.dto.view;

import java.util.List;

import org.sopt.carena.healthtip.domain.HealthTip;

public record ReadHealthTipTickerView(
		List<HealthTipListElement> result
) {
	public static ReadHealthTipTickerView from(List<HealthTip> healthTips) {
		return new ReadHealthTipTickerView(healthTips.stream().map(HealthTipListElement::from).toList());
	}
}
