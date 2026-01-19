package org.sopt.carena.diet.domain.value;

import java.util.List;
import java.util.Map;

public record RecommendedFoods(Map<String, List<String>> categories) {

    public RecommendedFoods {
        categories = categories != null
                ? Map.copyOf(categories)
                : Map.of();
    }

    public boolean isEmpty() {
        return categories.isEmpty();
    }
}