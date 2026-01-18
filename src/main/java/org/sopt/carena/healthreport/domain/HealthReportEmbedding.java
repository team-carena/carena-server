package org.sopt.carena.healthreport.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HealthReportEmbedding {
	private long id;
	private long memberId;
	private long healthReportId;

	private String embeddingText;
	private float[] embedding;

	@Builder
	private HealthReportEmbedding(
			final long id,
			final long memberId,
			final long healthReportId,
			final String embeddingText,
			final float[] embedding
	) {
		this.id = id;
		this.memberId = memberId;
		this.healthReportId = healthReportId;
		this.embeddingText = embeddingText;
		this.embedding = embedding;
	}
}
