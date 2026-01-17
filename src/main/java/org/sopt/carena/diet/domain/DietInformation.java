package org.sopt.carena.diet.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.healthtip.application.dto.command.CreateHealthTipCommand;
import org.sopt.carena.healthtip.domain.HealthTip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DietInformation {

    private final Long id;
    private final String title;
    private final String content;
    private final String reference;
    private final String referenceUrl;
    private final List<DietChunk> chunks;
    private final Map<String, List<String>> recommendedCategories;  // 추가
    private final List<String> cautionaryFoods;

    @Builder
    public DietInformation(
            Long id,
            String title,
            String content,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks,
            Map<String, List<String>> recommendedCategories,
            List<String> cautionaryFoods
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
        this.chunks = new ArrayList<>(chunks);
        this.recommendedCategories = recommendedCategories != null
                ? Map.copyOf(recommendedCategories)
                : Map.of();
        this.cautionaryFoods = cautionaryFoods != null
                ? List.copyOf(cautionaryFoods)
                : List.of();
    }
    public static DietInformation create(
            String title,
            String content,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks,
            Map<String, List<String>> recommendedCategories,
            List<String> cautionaryFoods
    ) {
        return DietInformation.builder()
                .title(title)
                .content(content)
                .reference(reference)
                .referenceUrl(referenceUrl)
                .chunks(chunks)
                .recommendedCategories(recommendedCategories)
                .cautionaryFoods(cautionaryFoods)
                .build();
    }
    /*
    public static DietInformation create(CreateDietCommand command) {
        return DietInformation.builder()
                .title(command.title())
                .reference(command.reference())
                .referenceUrl(command.referenceUrl())
                .chunks(command.chunks())
                .build();
    }

     */

    public String title() {
        return title;
    }
    public List<DietChunk> chunks() {
        return chunks;
    }

    public Long id() {
        return id;
    }

    public Map<String, List<String>> recommendedCategories() {
        return Map.copyOf(recommendedCategories);
    }

    public List<String> cautionaryFoods() {
        return List.copyOf(cautionaryFoods);
    }

    public String content() {
        return content;
    }

    public String reference() {
        return reference;
    }
}