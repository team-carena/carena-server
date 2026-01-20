package org.sopt.carena.recommend.adapter.out.persistence.mapper;

import org.sopt.carena.recommend.adapter.out.persistence.entity.RecommendedMealEntity;
import org.sopt.carena.recommend.domain.RecommendedMeal;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;

public class RecommendedMealMapper {
	public static RecommendedMeal toDomain(final RecommendedMealEntity entity) {
		return RecommendedMeal.builder()
				.id(entity.getId())
				.meal(entity.getMeal())
				.description(entity.getDescription())
				.baseDocumentId(entity.getBaseDocumentId())
				.baseDocumentTitle(entity.getBaseDocumentTitle())
				.memberId(entity.getMemberEntity().getId())
				.healthReportId(entity.getHealthReportEntity().getId())
				.build();

	}

	public static RecommendedMealEntity toEntity(
			final RecommendedMeal domain,
			final MemberEntity memberEntity,
			final HealthReportEntity healthReportEntity
	) {
		return RecommendedMealEntity.builder()
				.meal(domain.getMeal())
				.description(domain.getDescription())
				.baseDocumentId(domain.getBaseDocumentId())
				.baseDocumentTitle(domain.getBaseDocumentTitle())
				.memberEntity(memberEntity)
				.healthReportEntity(healthReportEntity)
				.build();
	}
}
