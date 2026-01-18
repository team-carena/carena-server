/*
package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.in.CreateDietRecommendUseCase;
import org.sopt.carena.diet.application.port.in.GetDietRecommendUseCase;
import org.sopt.carena.diet.application.port.out.LoadDietRecommendPort;
import org.sopt.carena.diet.application.port.out.SaveDietRecommendPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.sopt.carena.diet.domain.value.RecommendationContent;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEmbeddingEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportNotFoundException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DietRecommendService implements CreateDietRecommendUseCase, GetDietRecommendUseCase {

    private final SaveDietRecommendPort saveDietRecommendPort;
    private final LoadDietRecommendPort loadDietRecommendPort;
    private final HealthReportEmbeddingRepository healthEmbeddingRepository;
    private final DietInformationJpaRepository dietRepository;
    private final ChatClient chatClient;

    @Override
    @Transactional
    public DietRecommend createRecommendation(Long memberId) {
        // 1. 최신 건강검진 임베딩 조회
        HealthReportEmbeddingEntity healthEmbedding = healthEmbeddingRepository
                .findTopByMemberEntityIdOrderByHealthReportEntityHealthCheckDateDesc(memberId)
                .orElseThrow(() -> new HealthReportNotFoundException(memberId));

        // 2. Retrieval: 유사 식단 검색
        String vectorString = convertToVectorString(healthEmbedding.getEmbedding());
        List<DietInformationEntity> topDiets = dietRepository
                .findRecommendedByHealthEmbedding(vectorString, 5, 0);

        // 3. Augmentation: 컨텍스트 구성
        String context = buildContext(healthEmbedding, topDiets);

        // 4. Generation: LLM으로 추천 생성
        RecommendationContent content = generateRecommendation(context);

        // 5. 도메인 생성 및 저장
        DietRecommend dietRecommend = DietRecommend.create(
                memberId,
                healthEmbedding.getHealthReportEntity().getId(),
                content
        );

        return saveDietRecommendPort.save(dietRecommend);
    }

    @Override
    public Optional<DietRecommend> getLatestRecommendation(Long memberId) {
        return loadDietRecommendPort.findLatestByMemberId(memberId);
    }
}*/
