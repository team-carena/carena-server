package org.sopt.carena.diet.domain.value;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DietChunk {

    private long id;
    private final DietSection section;
    private final String content;
    private final int chunkOrder;
    // 임베딩 관련
    private String embeddingText;
    private float[] embedding;

    @Builder
    private DietChunk(
            long id,
            DietSection section,
            String content,
            int chunkOrder,
            String embeddingText,
            float[] embedding
    ) {
        this.section = section;
        this.content = content;
        this.chunkOrder = chunkOrder;
        this.embeddingText = embeddingText;
        this.embedding = embedding;
    }

    public void assignEmbedding(String embeddingText, float[] embedding) {
        this.embeddingText = embeddingText;
        this.embedding = embedding;
    }
}