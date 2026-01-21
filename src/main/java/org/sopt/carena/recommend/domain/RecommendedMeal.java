package org.sopt.carena.recommend.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendedMeal {
	private Long id;
	private Long memberId;
	private Long healthReportId;
	private LocalDateTime createdAt;

	private String meal;
	private String description;

	private Long baseDocumentId;
	private String baseDocumentTitle;

	@Builder
	private RecommendedMeal(
			final Long id,
			final Long memberId,
			final Long healthReportId,
			final LocalDateTime createdAt,
			final String meal,
			final String description,
			final Long baseDocumentId,
			final String baseDocumentTitle
	) {
		this.id = id;
		this.memberId = memberId;
		this.healthReportId = healthReportId;
		this.createdAt = createdAt;
		this.meal = meal;
		this.description = description;
		this.baseDocumentId = baseDocumentId;
		this.baseDocumentTitle = baseDocumentTitle;
	}
}
