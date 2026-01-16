package org.sopt.carena.diet.domain;

import java.util.Arrays;

public enum DietSection {

    NECESSITY("식사요법의 필요성"),
    PRACTICE("식사요법의 실제"),
    RECOMMENDED_FOOD("권장 식품"),
    CAUTION_FOOD("주의 식품"),
    EXTRA_CAUTION("그 외 주의사항");

    private final String description;

    DietSection(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public static DietSection from(String rawSection) {
        if (rawSection == null || rawSection.isBlank()) {
            throw new IllegalArgumentException("DietSection is null or blank");
        }

        return Arrays.stream(values())
                .filter(section ->
                        section.description.equals(rawSection.trim())
                                || section.name().equalsIgnoreCase(rawSection.trim())
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "알수없는 섹션 이름: " + rawSection
                        )
                );
    }
}