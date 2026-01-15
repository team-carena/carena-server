package org.sopt.carena.diet.domain;

import lombok.Getter;

@Getter
public class DietChunk {

    private String chunkId;
    //private final String documentId;
    //private final String documentTitle;
    private final DietSection section;
    private final String content;
    private final int chunkOrder;
    // 임베딩 관련
    private String embeddingText;
    private float[] embedding;

    public DietChunk(
            //String chunkId,
            //String documentId,
            //String documentTitle,
            DietSection section,
            String content,
            int chunkOrder
    ) {
        //this.chunkId = chunkId;
        //this.documentId = documentId;
        //this.documentTitle = documentTitle;
        this.section = section;
        this.content = content;
        this.chunkOrder = chunkOrder;
    }
    public void assignEmbedding(String embeddingText, float[] embedding) {
        this.embeddingText = embeddingText;
        this.embedding = embedding;
    }

    public void assignChunkId(String chunkId) {
        this.chunkId = chunkId;
    }
/*
    public DietSection section() { return section; }
    public String content() { return content; }

 */
}