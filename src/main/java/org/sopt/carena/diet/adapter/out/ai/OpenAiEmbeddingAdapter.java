package org.sopt.carena.diet.adapter.out.ai;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.port.out.EmbeddingGenerator;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingGenerator {

    private final EmbeddingClient embeddingClient;

    @Override
    public float[] embed(final String text) {
        return embeddingClient.embed(text)
                .getResults()
                .get(0)
                .getOutput();
    }
}
