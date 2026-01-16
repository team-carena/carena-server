package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringAiEmbeddingClient implements EmbeddingClient {

    private final EmbeddingModel embeddingModel;

    @Override
    public EmbeddingResponse embed(final String text) {
        return embeddingModel.embedForResponse(List.of(text));
    }

    @Override
    public EmbeddingResponse embedBatch(final List<String> texts) {
        return embeddingModel.embedForResponse(texts);
    }
}