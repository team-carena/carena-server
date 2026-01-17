package org.sopt.carena.diet.application.port.out;

import java.util.List;

public interface EmbeddingGenerator {
    //단일 텍스트 임베딩
    float[] embed(String text);
    // 배치 텍스트 임베딩
    List<float[]> embedBatch(List<String> texts);
}
