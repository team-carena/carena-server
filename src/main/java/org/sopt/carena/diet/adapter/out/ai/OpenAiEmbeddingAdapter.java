package org.sopt.carena.diet.adapter.out.ai;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.port.out.EmbeddingGenerator;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingGenerator {

    private final EmbeddingClient embeddingClient;

    @Override
    public float[] embed(final String text) {
        var response = embeddingClient.embed(text);
        if (response == null  || response.getResults().isEmpty()
                || response.getResults().get(0).getOutput() == null) {
            throw new EmbeddingResultNullException();
        }
        return response.getResults().get(0).getOutput();
    }
}
