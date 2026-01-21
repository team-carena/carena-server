package org.sopt.carena.diet.application.dto.view;

import org.sopt.carena.diet.domain.DietInformation;

import java.util.List;
import java.util.Map;
public record DietDetailResultView(
        String id,
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

    public static DietDetailResultView from(DietInformation dietInformation) {
        return new DietDetailResultView(
                String.valueOf(dietInformation.getId()),
                dietInformation.getTitle(),
                dietInformation.getContent(),
                dietInformation.getRecommendedFoods().categories(),
                dietInformation.getCautionaryFoods().foods(),
                dietInformation.getReference()
        );
    }
}