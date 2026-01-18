package org.sopt.carena.infrastructure.embedding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;
import org.springframework.ai.embedding.EmbeddingResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BatchEmbeddingResult(
        List<EmbeddingData> embeddings
) {
    public record EmbeddingData(
            float[] vector,
            int index
    ) {}

    public static BatchEmbeddingResult from(EmbeddingResponse response) {
        if (response == null || response.getResults().isEmpty()) {
            throw new EmbeddingResultNullException();
        }

        List<EmbeddingData> embeddings = response.getResults().stream()
                .map(result -> new EmbeddingData(
                        result.getOutput(),
                        result.getIndex()
                ))
                .toList();

        return new BatchEmbeddingResult(embeddings);
    }
}