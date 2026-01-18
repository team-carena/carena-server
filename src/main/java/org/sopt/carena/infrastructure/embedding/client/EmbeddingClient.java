package org.sopt.carena.infrastructure.embedding.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.infrastructure.embedding.dto.BatchEmbeddingResult;
import org.sopt.carena.infrastructure.embedding.dto.SingleEmbeddingResult;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmbeddingClient {
    private final EmbeddingModel embeddingModel;

    public SingleEmbeddingResult embed(final String text) {
        EmbeddingResponse response = embeddingModel.embedForResponse(List.of(text));
        return SingleEmbeddingResult.from(response);
    }

    public BatchEmbeddingResult embedBatch(final List<String> texts) {
        EmbeddingResponse response = embeddingModel.embedForResponse(texts);
        return BatchEmbeddingResult.from(response);
    }
}