package org.sopt.carena.healthreport.adapter.out.persistence.mapper;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.healthreport.domain.HealthReportEmbedding;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;

public class HealthReportEmbeddingMapper {
	public static HealthReportEmbedding toDomain(final HealthReportEmbeddingEntity healthReportEmbeddingEntity){
		return HealthReportEmbedding.builder()
				.id(healthReportEmbeddingEntity.getId())
				.embeddingText(healthReportEmbeddingEntity.getEmbeddingText())
				.embedding(healthReportEmbeddingEntity.getEmbedding())
				.memberId(healthReportEmbeddingEntity.getMemberEntity().getId())
				.healthReportId(healthReportEmbeddingEntity.getHealthReportEntity().getId())
				.build();
	}

	public static HealthReportEmbeddingEntity toEntity(
			final HealthReportEmbedding domain,
			final MemberEntity memberEntity,
			final HealthReportEntity healthReportEntity
	) {
		return HealthReportEmbeddingEntity.builder()
				.embeddingText(domain.getEmbeddingText())
				.embedding(domain.getEmbedding())
				.memberEntity(memberEntity)
				.healthReportEntity(healthReportEntity)
				.build();

	}
}
