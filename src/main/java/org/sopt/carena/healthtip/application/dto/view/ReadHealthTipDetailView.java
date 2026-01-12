package org.sopt.carena.healthtip.application.dto.view;

import java.util.List;

import org.sopt.carena.healthtip.domain.HealthTip;
import org.sopt.carena.healthtip.domain.value.Hashtag;

public record ReadHealthTipDetailView(
		long id,
		String title,
		String subTitle,
		String content,
		String reference,
		List<String> hashtags
) {
	public static ReadHealthTipDetailView from(final HealthTip healthTip) {
		return new ReadHealthTipDetailView(
				healthTip.getId(),
				healthTip.getTitle(),
				healthTip.getSubTitle(),
				healthTip.getContent(),
				healthTip.getReference(),
				healthTip.getHashtags().stream()
						.map(Hashtag::name).toList()
		);
	}
}
