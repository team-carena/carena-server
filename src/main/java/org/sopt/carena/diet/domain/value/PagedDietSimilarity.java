package org.sopt.carena.diet.domain.value;

import java.util.List;

/**
 * 페이징된 식단 유사도 결과
 */
public record PagedDietSimilarity(
                List<DietChunkSimilarity> content,
                boolean hasNext
        ) {
    public PagedDietSimilarity {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        content = List.copyOf(content);
    }

    public int size() {
        return content.size();
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }
}