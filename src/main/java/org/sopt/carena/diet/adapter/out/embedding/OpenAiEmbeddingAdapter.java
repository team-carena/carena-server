package org.sopt.carena.diet.adapter.out.embedding;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.dto.EmbeddingResult;
import org.sopt.carena.diet.application.port.out.EmbeddingGenerator;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingGenerator {

    private final EmbeddingClient embeddingClient;

    @Override
    public float[] embed(final String text) {
        EmbeddingResult result = embeddingClient.embed(text);
        if (result == null  || result.data() == null || result.data().isEmpty()) {
            throw new EmbeddingResultNullException();
        }
        return result.getFirstEmbedding();
    }
    @Override
    public List<float[]> embedBatch(final List<String> texts) {
        EmbeddingResult result = embeddingClient.embedBatch(texts);

        if (result == null || result.data() == null || result.data().isEmpty()) {
            throw new EmbeddingResultNullException();
        }
        return result.getAllEmbeddings();
    }
}
