package org.sopt.carena.healthtip.application.dto.commend;

import java.util.List;

import org.sopt.carena.healthtip.adapter.in.request.CreateHealthTipRequest;

public record CreateHealthTipCommend(
		String title,
		String subTitle,
		String content,
		String reference,
		List<String> hashtags
) {
	public static CreateHealthTipCommend from(final CreateHealthTipRequest createHealthTipDto) {
		return new CreateHealthTipCommend(
				createHealthTipDto.title(),
				createHealthTipDto.subTitle(),
				createHealthTipDto.content(),
				createHealthTipDto.reference(),
				createHealthTipDto.hashtags()
		);
	}
}
