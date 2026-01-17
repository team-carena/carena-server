package org.sopt.carena.diet.application.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmbeddingResult(
        List<EmbeddingData> data
) {
    public record EmbeddingData(
            float[] embedding,
            int index
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
