/*
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
    private final String title;
    private final RecommendationContent content;  // VO
    private final LocalDateTime createdAt;

    @Builder
    private DietRecommend(
            Long id,
            Long memberId,
            Long healthReportId,
            Long dietInformationId,
            String title,
            RecommendationContent content,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.healthReportId = healthReportId;
        this.dietInformationId = dietInformationId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Factory method
    public static DietRecommend create(
            Long memberId,
            Long healthReportId,
            Long dietInformationId,
            String title,
            RecommendationContent content
    ) {
        return DietRecommend.builder()
                .memberId(memberId)
                .healthReportId(healthReportId)
                .dietInformationId(dietInformationId)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
*/
