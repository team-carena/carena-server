package org.sopt.carena.diet.domain;

import lombok.Getter;

@Getter
public class DietChunk {

    private String id;
    private final DietSection section;
    private final String content;
    private final int chunkOrder;
    // 임베딩 관련
    private String embeddingText;
    private float[] embedding;

    public DietChunk(
            DietSection section,
            String content,
            int chunkOrder
    ) {
        this.section = section;
        this.content = content;
        this.chunkOrder = chunkOrder;
    }
    public void assignEmbedding(String embeddingText, float[] embedding) {
        this.embeddingText = embeddingText;
        this.embedding = embedding;
    }
}