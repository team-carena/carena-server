/*
package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.in.GetDietRecommendUseCase;
import org.sopt.carena.diet.application.port.out.LoadDietRecommendPort;
import org.sopt.carena.diet.application.port.out.SaveDietRecommendPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportEmbeddingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDietRecommendService implements GetDietRecommendUseCase {

    private final SaveDietRecommendPort saveDietRecommendPort;
    private final LoadDietRecommendPort loadDietRecommendPort;
    private final HealthReportEmbeddingRepository healthEmbeddingRepository;
    private final DietInformationJpaRepository dietRepository;
    //private final ChatClient chatClient;

    private static final int MAX_RECOMMENDATIONS = 13;


    @Override
    public Optional<DietRecommend> getLatestRecommendation(Long memberId) {
        return loadDietRecommendPort.findLatestByMemberId(memberId);
    }


    */
/**
     * RecommendedFood를 문자열로 포맷팅
     *//*

    private String formatRecommendedFood(DietInformationEntity diet) {
        if (diet.getRecommendedFood() == null) {
            return "";
        }
        return diet.getRecommendedFood().getFoodNames();
    }

    */
/**
     * float[] 배열을 PostgreSQL vector 포맷으로 변환
     *//*

    private String convertToVectorString(float[] embedding) {
        return Arrays.stream(embedding)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
*/
