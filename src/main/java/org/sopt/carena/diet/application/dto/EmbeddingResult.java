package org.sopt.carena.diet.application.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

public record EmbeddingResult(
        List<EmbeddingData> data,
        @JsonIgnoreProperties(ignoreUnknown = true)
        String model,
        @JsonIgnoreProperties(ignoreUnknown = true)
        Usage usage
) {
    public record EmbeddingData(
            float[] embedding,
            int index
    ) {}

    public record Usage(
            int promptTokens,
            int totalTokens
    ) {}

    public float[] getFirstEmbedding() {
        if (data == null || data.isEmpty()) {
            return new float[0];
        }
        return data.get(0).embedding();
    }
    public List<float[]> getAllEmbeddings() {
        if (data == null) {
            return List.of();
        }
        return data.stream()
                .map(EmbeddingData::embedding)
                .toList();
    }
}
