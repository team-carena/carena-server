package org.sopt.carena.healthtip.adapter.in.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHealthTipRequest(
		@NotBlank
		String title,

		@NotBlank
		String subTitle,

		@NotBlank
		String content,

		@NotBlank
		String reference,

		@NotNull
		List<@NotBlank String> hashtags
) {
}
