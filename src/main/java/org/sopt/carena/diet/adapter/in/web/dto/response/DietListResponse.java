package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.application.dto.view.DietListResultView;

import java.util.List;

public record DietListResponse(
        List<DietItem> diets,
        boolean hasNext
) {
    public record DietItem(Long id, String title) {}

    // DietListResult -> DietListResponse 변환
    public static DietListResponse from(DietListResultView result) {
        List<DietItem> items = result.items().stream()
                .map(resultItem -> new DietItem(
                        resultItem.id(),
                        resultItem.title()
                ))
                .toList();

        return new DietListResponse(items, result.hasNext());
    }
}