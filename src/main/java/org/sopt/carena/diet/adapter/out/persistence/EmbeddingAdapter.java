package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.diet.domain.value.EmbeddingVector;
import org.sopt.carena.diet.exception.embedding.EmbeddingFailedException;
import org.sopt.carena.infrastructure.embedding.client.EmbeddingClient;
import org.sopt.carena.infrastructure.embedding.dto.BatchEmbeddingResult;
import org.sopt.carena.infrastructure.embedding.dto.SingleEmbeddingResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmbeddingAdapter implements EmbeddingPort {
    private final EmbeddingClient embeddingClient;

    @Override
    public EmbeddingVector embed(final String text) {
        try {
            SingleEmbeddingResult result = embeddingClient.embed(text);
            return mapToDomain(result);
        } catch (IllegalArgumentException e) {
            log.error("임베딩실패: {}", text, e);
            throw new EmbeddingFailedException();
        }
    }

    @Override
    public List<EmbeddingVector> embedBatch(final List<String> texts) {
        try {
            BatchEmbeddingResult result = embeddingClient.embedBatch(texts);
            return mapToDomain(result);
        } catch (IllegalArgumentException e) {
            log.error("배치로 임베딩 실패", e);
            throw new EmbeddingFailedException();
        }
    }

    private EmbeddingVector mapToDomain(final SingleEmbeddingResult result) {
        return new EmbeddingVector(result.embedding());
    }

    private List<EmbeddingVector> mapToDomain(final BatchEmbeddingResult result) {
        return result.embeddings().stream()
                .map(data -> new EmbeddingVector(data.vector()))
                .toList();
    }
}
