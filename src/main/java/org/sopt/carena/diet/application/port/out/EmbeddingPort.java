package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.value.EmbeddingVector;

import java.util.List;

public interface EmbeddingPort {
    //단일 텍스트 임베딩
    EmbeddingVector embed(String text);
    // 배치 텍스트 임베딩
    List<EmbeddingVector> embedBatch(List<String> texts);
}
