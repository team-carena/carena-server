package org.sopt.carena.diet.application.dto.view;

import org.sopt.carena.diet.domain.DietInformation;

import java.util.List;

public record DietListResultView(
        List<DietItem> items,
        boolean hasNext
) {
    public record DietItem(Long id, String title) {
    }

    /**
     * DietInformation 리스트로부터 DietListResultView 생성
     */
    public static DietListResultView from(List<DietInformation> diets, boolean hasNext) {
        List<DietItem> items = diets.stream()
                .map(diet -> new DietItem(diet.getId(), diet.getTitle()))
                .toList();

        return new DietListResultView(items, hasNext);
    }
}