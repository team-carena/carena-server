package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.EmbeddingResult;
import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.diet.exception.embedding.EmbeddingResultNullException;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmbeddingAdapter implements EmbeddingPort {
    private final EmbeddingModel embeddingModel;
    //private final EmbeddingClient embeddingClient;

    //todo: EmbeddingResult사용하는걸로 교체후 그것만 남기기
    /*
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
     */

    @Override
    public float[] embed(final String text) {
        EmbeddingResponse result = embeddingModel.embedForResponse(List.of(text));
        if (result == null  || result.getResults().isEmpty()) {
            throw new EmbeddingResultNullException();
        }
        return toFloatArray(result.getResults().get(0).getOutput());
    }
    @Override
    public List<float[]> embedBatch(final List<String> texts) {
        EmbeddingResponse response = embeddingModel.embedForResponse(texts);

        if (response == null || response.getResults().isEmpty()) {
            throw new EmbeddingResultNullException();
        }
        return response.getResults().stream()
                .map(result -> toFloatArray(result.getOutput()))
                .toList();
    }
    private float[] toFloatArray(float[] doubles) {
        float[] floats = new float[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            floats[i] = (float) doubles[i];
        }
        return floats;
    }
}
