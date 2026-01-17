package org.sopt.carena.diet.adapter.out.ai;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.dto.EmbeddingResult;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SpringAiEmbeddingClient implements EmbeddingClient {

    private final EmbeddingModel embeddingModel;

    @Override
    public EmbeddingResult embed(final String text) {
        EmbeddingResponse response = embeddingModel.embedForResponse(List.of(text));
        return toEmbeddingResult(response);
    }

    @Override
    public EmbeddingResult embedBatch(final List<String> texts) {
        EmbeddingResponse response = embeddingModel.embedForResponse(texts);
        return toEmbeddingResult(response);
    }

    // 응답을 자체 DTO로 변환
    private EmbeddingResult toEmbeddingResult(EmbeddingResponse response) {
        List<EmbeddingResult.EmbeddingData> data = IntStream.range(0, response.getResults().size())
                .mapToObj(i -> new EmbeddingResult.EmbeddingData(
                        response.getResults().get(i).getOutput(),
                        i
                ))
                .toList();

        return new EmbeddingResult(
                data
        );
    }
}