package org.sopt.carena.diet.domain;

import lombok.Getter;

@Getter
public class DietSummary {
    private final Long id;
    private final String title;

    public DietSummary(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}