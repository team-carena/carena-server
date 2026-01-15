package org.sopt.carena.diet.application.port.out;

import org.springframework.ai.embedding.EmbeddingResponse;

public interface EmbeddingClient {
    EmbeddingResponse embed(String text);
    EmbeddingResponse embedBatch(java.util.List<String> texts);
}
