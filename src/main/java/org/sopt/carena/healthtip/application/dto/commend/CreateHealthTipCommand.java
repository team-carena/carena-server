package org.sopt.carena.healthtip.application.dto.commend;

import java.util.List;

import org.sopt.carena.healthtip.adapter.in.request.CreateHealthTipRequest;

public record CreateHealthTipCommand(
		String title,
		String subTitle,
		String content,
		String reference,
		List<String> hashtags
) {
	public static CreateHealthTipCommand from(final CreateHealthTipRequest createHealthTipDto) {
		return new CreateHealthTipCommand(
				createHealthTipDto.title(),
				createHealthTipDto.subTitle(),
				createHealthTipDto.content(),
				createHealthTipDto.reference(),
				createHealthTipDto.hashtags()
		);
	}
}
