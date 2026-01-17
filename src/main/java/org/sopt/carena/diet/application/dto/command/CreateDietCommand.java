package org.sopt.carena.diet.application.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CreateDietCommand {
    private final String title;
    private final String reference;
    private final String referenceUrl;
    private final List<DietChunkCommand> chunks;
    // 권장 식품
    private Map<String, List<String>> recommendedFoods;
    // 주의 식품
    private List<String> cautionaryFoods;

    @Getter
    @Builder
    public static class DietChunkCommand {
        private final String section;
        private final String content;
        private final int chunkOrder;
    }
}