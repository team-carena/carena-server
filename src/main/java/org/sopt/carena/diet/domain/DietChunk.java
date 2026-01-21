package org.sopt.carena.diet.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.carena.diet.domain.value.DietSection;

import java.util.Map;

@Getter
public class DietChunk {

    private long id;
    private final DietSection section;
    private final String content;
    private final int chunkOrder;
    // 임베딩 관련
    private String embeddingText;
    private float[] embedding;
    private Map<String, Object> metadata;

    @Builder
    private DietChunk(
            long id,
            DietSection section,
            String content,
            int chunkOrder,
            String embeddingText,
            float[] embedding,
            Map<String, Object> metadata
    ) {
        this.id = id;
        this.section = section;
        this.content = content;
        this.chunkOrder = chunkOrder;
        this.embeddingText = embeddingText;
        this.embedding = embedding;
        this.metadata = metadata;
    }

    public void assignEmbedding(String embeddingText, float[] embedding) {
        this.embeddingText = embeddingText;
        this.embedding = embedding;
    }
    public void assignMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}