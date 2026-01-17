package org.sopt.carena.diet.domain.value;

import org.sopt.carena.diet.exception.embedding.InvalidSectionNameException;
import org.sopt.carena.diet.exception.embedding.SectionNameNullException;

import java.util.Arrays;

public enum DietSection {

    NECESSITY("식사요법의 필요성"),
    PRACTICE("식사요법의 실제"),
    EXTRA_CAUTION("그 외 주의사항");

    private final String description;

    DietSection(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public static DietSection from(String rawSection) {
        return Arrays.stream(values())
                .filter(section ->
                        section.description.equals(rawSection.trim())
                                || section.name().equalsIgnoreCase(rawSection.trim())
                )
                .findFirst()
                .orElseThrow(InvalidSectionNameException::new
                );
    }
}