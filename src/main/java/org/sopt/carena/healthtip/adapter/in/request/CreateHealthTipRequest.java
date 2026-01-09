package org.sopt.carena.healthtip.adapter.in.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CreateHealthTipRequest(
		@NotBlank
		String title,

		@NotBlank
		String subTitle,

		@NotBlank
		String content,

		@NotBlank
		String reference,

		List<String> hashtags
) {
}
