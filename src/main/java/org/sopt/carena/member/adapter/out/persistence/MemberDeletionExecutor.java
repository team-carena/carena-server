package org.sopt.carena.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.sopt.carena.recommend.adapter.out.persistence.repository.RecommendedMealRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeletionExecutor {
    private final HealthReportEmbeddingRepository healthReportEmbeddingRepository;
    private final HealthReportRepository healthReportRepository;
    private final RecommendedMealRepository recommendedMealRepository;
    private final MemberJpaRepository memberJpaRepository;

    public void deleteAll(Long memberId) {
        healthReportEmbeddingRepository.deleteAllByMemberEntityId(memberId);
        healthReportRepository.deleteAllByMemberEntityId(memberId);
        recommendedMealRepository.deleteAllByMemberEntityId(memberId);
        memberJpaRepository.deleteById(memberId);
    }
}
