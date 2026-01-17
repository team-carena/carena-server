package org.sopt.carena.diet.application.dto.view;

import java.util.List;
import java.util.Map;
public record DietDetailResultView(
        Long id,
        String title,
        String content,
        Map<String, List<String>> recommendedCategories,
        List<String> cautionaryFoods,
        String reference
) {
    public DietDetailResultView {
        recommendedCategories = Map.copyOf(recommendedCategories);
        cautionaryFoods = List.copyOf(cautionaryFoods);
    }
}