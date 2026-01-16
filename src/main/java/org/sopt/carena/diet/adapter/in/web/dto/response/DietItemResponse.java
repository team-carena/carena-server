package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.domain.DietSummary;

public record DietItemResponse(
        Long id,
        String title
) {
    public static DietItemResponse from(DietSummary summary) {
        return new DietItemResponse(
                summary.getId(),
                summary.getTitle()
        );
    }
}