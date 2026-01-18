package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.domain.DietRecommend;

public record DietRecommendResponse(
        String title,
        String content
) {
    public static DietRecommendResponse from(DietRecommend domain) {
        return new DietRecommendResponse(
                domain.getContent().title(),
                domain.getContent().content()
        );
    }
}
