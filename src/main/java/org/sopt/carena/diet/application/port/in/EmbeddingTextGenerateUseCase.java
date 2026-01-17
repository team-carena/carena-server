package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.value.DietChunk;

public interface EmbeddingTextGenerateUseCase {
    String generate(DietChunk chunk,String documentTitle);
}
