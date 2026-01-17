package org.sopt.carena.diet.domain.value;

import java.util.List;
import java.util.Map;

public record DietDetail(
        Long id,
        String title,
        String content,
        Map<String, List<String>> recommendedCategories,
        List<String> cautionaryFoods,
        String reference
) {
    public DietDetail {
        recommendedCategories = Map.copyOf(recommendedCategories);
        cautionaryFoods = List.copyOf(cautionaryFoods);
    }
}
