package org.sopt.carena.diet.application.dto.view;

import java.util.List;

public record DietListResultView(
        List<DietItem> items,
        boolean hasNext
) {
    public record DietItem(Long id, String title) {}
}