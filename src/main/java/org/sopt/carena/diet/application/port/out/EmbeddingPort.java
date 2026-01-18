package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.application.dto.EmbeddingResult;

import java.util.List;

public interface EmbeddingPort {
    //단일 텍스트 임베딩
    float[] embed(String text);
    // 배치 텍스트 임베딩
    List<float[]> embedBatch(List<String> texts);

    //EmbeddingResult embed(String text);
    //EmbeddingResult embedBatch(List<String> texts);
}
