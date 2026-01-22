package org.sopt.carena.diet.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.diet.domain.value.CautionaryFoods;
import org.sopt.carena.diet.domain.value.RecommendedFoods;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final LocalDateTime createdAt;

    @Builder
    private DietInformation(
            Long id,
            String title,
            String content,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks,
            RecommendedFoods recommendedFoods,
            CautionaryFoods cautionaryFoods,
            LocalDateTime createdAt

    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
        this.chunks = new ArrayList<>(chunks);
        this.recommendedFoods = recommendedFoods;
        this.cautionaryFoods = cautionaryFoods;
        this.createdAt = createdAt;
    }

    public String getCombinedChunkContent() {
        return chunks.stream()
                .map(DietChunk::getContent)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));
    }
}