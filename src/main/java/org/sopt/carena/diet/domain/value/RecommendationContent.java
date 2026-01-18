package org.sopt.carena.diet.domain.value;

public record RecommendationContent(
        String title,
        String content
) {
    public RecommendationContent {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다");
        }
    }
}

