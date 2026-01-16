package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.domain.DietDetail;

import java.util.List;

public record DietDetailResponse(
        Long id,
        String title,
        String content,
        List<String> recommends,
        List<String> cautionary,
        String reference
) {
    public static DietDetailResponse from(final DietDetail dietDetail) {

        return new DietDetailResponse(
                dietDetail.getId(),
                dietDetail.getTitle(),
                dietDetail.getContent(),
                dietDetail.getRecommends(),
                dietDetail.getCautionary(),
                dietDetail.getReference()
        );
    }
}

