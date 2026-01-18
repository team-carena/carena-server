package org.sopt.carena.infrastructure.embedding.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;
import org.springframework.ai.embedding.EmbeddingResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SingleEmbeddingResult(
        float[] embedding
) {
    public static SingleEmbeddingResult from(EmbeddingResponse response) {
        if (response == null || response.getResults().isEmpty()) {
            throw new EmbeddingResultNullException();
        }
        return new SingleEmbeddingResult(
                response.getResults().get(0).getOutput()
        );
    }
}