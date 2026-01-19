package org.sopt.carena.diet.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.domain.value.CautionaryFoods;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.value.RecommendedFoods;
import org.sopt.carena.healthtip.application.dto.command.CreateHealthTipCommand;
import org.sopt.carena.healthtip.domain.HealthTip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DietInformation {

    private Long id;
    private final String title;
    private final String content;
    private final String reference;
    private final String referenceUrl;
    private final List<DietChunk> chunks;
    private final RecommendedFoods recommendedFoods;
    private final CautionaryFoods cautionaryFoods;

    @Builder
    private DietInformation(
            Long id,
            String title,
            String content,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks,
            RecommendedFoods recommendedFoods,
            CautionaryFoods cautionaryFoods

    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
        this.chunks = new ArrayList<>(chunks);
        this.recommendedFoods = recommendedFoods;
        this.cautionaryFoods = cautionaryFoods;
    }
}