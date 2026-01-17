package org.sopt.carena.diet.application.dto.command;

import java.util.List;
import java.util.Map;

public record CreateDietCommand(
        String title,
        String reference,
        String referenceUrl,
        List<DietChunkCommand> chunks,
        // 권장 식품
        Map<String, List<String>> recommendedCategories,
        // 주의 식품
        List<String> cautionaryFoods
) {

    public record DietChunkCommand(
            String section,
            String content,
            int chunkOrder
    ) {}
}