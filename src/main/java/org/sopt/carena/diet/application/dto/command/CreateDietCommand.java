package org.sopt.carena.diet.application.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateDietCommand {
    private final String title;
    private final String content;
    private final String reference;
    private final String referenceUrl;
    private final List<DietChunkCommand> chunks;

    @Getter
    @Builder
    public static class DietChunkCommand {
        private final String section;
        private final String content;
        private final int chunkOrder;
    }
}