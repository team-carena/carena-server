package org.sopt.carena.recommend.adapter.out.persistence;

import java.util.Optional;

import org.sopt.carena.healthreport.application.port.out.SaveRecommendedMealPort;
import org.sopt.carena.recommend.adapter.out.persistence.entity.RecommendedMealEntity;
import org.sopt.carena.recommend.adapter.out.persistence.mapper.RecommendedMealMapper;
import org.sopt.carena.recommend.adapter.out.persistence.repository.RecommendedMealRepository;
import org.sopt.carena.recommend.application.port.out.GetLatestRecommendedMealPort;
import org.sopt.carena.recommend.domain.RecommendedMeal;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedMealPersistenceAdapter implements SaveRecommendedMealPort, GetLatestRecommendedMealPort {
	private final RecommendedMealRepository recommendedMealRepository;
	private final MemberJpaRepository memberJpaRepository;
	private final HealthReportRepository healthReportRepository;

	@Transactional
	public RecommendedMeal saveRecommendedMeal(final RecommendedMeal recommendedMeal) {
		MemberEntity memberProxy = memberJpaRepository.getReferenceById(recommendedMeal.getMemberId());
		HealthReportEntity healthReportProxy = healthReportRepository.getReferenceById(recommendedMeal.getHealthReportId());

		RecommendedMealEntity recommendedMealEntity = recommendedMealRepository.save(RecommendedMealMapper.toEntity(recommendedMeal, memberProxy, healthReportProxy));

		return RecommendedMealMapper.toDomain(recommendedMealEntity);
	}

	public Optional<RecommendedMeal> getRecommendedMealById(final long memberId) {
		return recommendedMealRepository.findTopByMemberEntityIdOrderByCreatedAtDesc(memberId)
				.map(RecommendedMealMapper::toDomain);
	}
}
