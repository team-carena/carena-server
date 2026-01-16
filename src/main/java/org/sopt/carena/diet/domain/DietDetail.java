package org.sopt.carena.diet.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DietDetail {
    private final Long id;
    private final String title;
    private final String content;
    private final List<String> recommends;
    private final List<String> cautionary;
    private final String reference;

    public DietDetail(
            Long id,
            String title,
            String content,
            List<String> recommends,
            List<String> cautionary,
            String reference
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.recommends = new ArrayList<>(recommends);
        this.cautionary = new ArrayList<>(cautionary);
        this.reference = reference;
    }
}
