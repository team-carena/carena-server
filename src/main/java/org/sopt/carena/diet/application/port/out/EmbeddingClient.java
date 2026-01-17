package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.application.dto.EmbeddingResult;

import java.util.List;

public interface EmbeddingClient {
    EmbeddingResult embed(String text);
    EmbeddingResult embedBatch(List<String> texts);
}
