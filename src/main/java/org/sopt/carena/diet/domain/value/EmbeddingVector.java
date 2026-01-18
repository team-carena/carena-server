package org.sopt.carena.diet.domain.value;

import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;

public record EmbeddingVector(
        float[] vector
) {
    public EmbeddingVector {
        if (vector == null || vector.length == 0) {
            throw new EmbeddingResultNullException();
        }
    }
}