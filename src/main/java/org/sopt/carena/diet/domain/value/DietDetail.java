package org.sopt.carena.diet.domain.value;

import lombok.Getter;

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
    // 방어적 복사가 필요하면 compact constructor 사용
    public DietDetail {
        recommendedCategories = Map.copyOf(recommendedCategories);
        cautionaryFoods = List.copyOf(cautionaryFoods);
    }
}
