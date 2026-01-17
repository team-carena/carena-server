package org.sopt.carena.diet.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class DietDetail {
    private final Long id;
    private final String title;
    private final String content;
    private final Map<String, List<String>> recommendedCategories;
    private final List<String> cautionaryFoods;
    private final String reference;

    public DietDetail(
            Long id,
            String title,
            String content,
            Map<String, List<String>> recommendedCategories,
            List<String> cautionaryFoods,
            String reference
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.recommendedCategories = recommendedCategories;
        this.cautionaryFoods = cautionaryFoods;
        this.reference = reference;
    }
}
