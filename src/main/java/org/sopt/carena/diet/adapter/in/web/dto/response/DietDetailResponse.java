package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.application.dto.view.DietDetailResultView;
import java.util.List;
import java.util.Map;

public record DietDetailResponse(
        Long id,
        String title,
        String content,
        Map<String, List<String>> recommends,
        List<String> cautionary,
        String reference
) {
    public static DietDetailResponse from(final DietDetailResultView result) {

        return new DietDetailResponse(
                result.id(),
                result.title(),
                result.content(),
                result.recommendedCategories(),
                result.cautionaryFoods(),
                result.reference()
        );
    }
}

