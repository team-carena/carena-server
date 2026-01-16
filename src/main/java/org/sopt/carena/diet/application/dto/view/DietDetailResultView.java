package org.sopt.carena.diet.application.dto.view;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DietDetailResultView {
    private final Long id;
    private final String title;
    private final String content;
    private final List<String> recommends;
    private final List<String> cautionary;
    private final String reference;
}