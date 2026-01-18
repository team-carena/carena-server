/*
package org.sopt.carena.diet.adapter.out.persistence.mapper;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietRecommendEntity;
import org.sopt.carena.diet.domain.DietRecommend;
import org.sopt.carena.diet.domain.value.RecommendationContent;
import org.springframework.stereotype.Component;

@Component
public class DietRecommendMapper {

    public DietRecommendEntity toEntity(DietRecommend domain) {
        return DietRecommendEntity.builder()
                .id(domain.getId())
                .memberId(domain.getMemberId())
                .healthReportId(domain.getHealthReportId())
                .title(domain.getContent().title())
                .content(domain.getContent().content())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public DietRecommend toDomain(DietRecommendEntity entity) {
        RecommendationContent content = new RecommendationContent(
                entity.getTitle(),
                entity.getContent()
        );

        return DietRecommend.builder()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .healthReportId(entity.getHealthReportId())
                .content(content)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}*/
