package org.sopt.carena.diet.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.diet.domain.value.RecommendationContent;

import java.time.LocalDateTime;

@Getter
public class DietRecommend {
    private final Long id;
    private final Long memberId;
    private final Long healthReportId;
    private final Long dietInformationId;
    private final String title;//제목
    private final String content;             // 추천 음식
    private final Double similarityScore;     // 유사도 점수
    private final LocalDateTime createdAt;

    @Builder
    private DietRecommend(
            Long id,
            Long memberId,
            Long healthReportId,
            Long dietInformationId,
            String title, //식단 제목
            String content, //추천 식단의 음식
            Double similarityScore,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.healthReportId = healthReportId;
        this.dietInformationId = dietInformationId;
        this.title = title;
        this.content = content;
        this.similarityScore = similarityScore;
        this.createdAt = createdAt;
    }

    // Factory method
    public static DietRecommend create(
            Long memberId,
            Long healthReportId,
            Long dietInformationId,
            String title,
            String content,
            Double similarityScore
    ) {
        return DietRecommend.builder()
                .memberId(memberId)
                .healthReportId(healthReportId)
                .dietInformationId(dietInformationId)
                .title(title)
                .content(content)
                .similarityScore(similarityScore)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

