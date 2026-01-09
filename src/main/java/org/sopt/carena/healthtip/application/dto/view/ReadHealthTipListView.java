package org.sopt.carena.healthtip.application.dto.view;

import java.util.List;

import org.sopt.carena.healthtip.domain.HealthTip;
import org.springframework.data.domain.Slice;

public record ReadHealthTipListView(
		List<HealthTipListElement> result,
		boolean hasNext
) {
	public static ReadHealthTipListView from(final Slice<HealthTip> healthTipList) {
		List<HealthTipListElement> elements = healthTipList.getContent().stream()
				.map(HealthTipListElement::from)
				.toList();

		return new ReadHealthTipListView(elements, healthTipList.hasNext());
	}

	private record HealthTipListElement(
			Long id,
			String title
	) {
		private static HealthTipListElement from(final HealthTip healthTip) {
			return new HealthTipListElement(healthTip.getId(), healthTip.getTitle());
		}
	}
}
