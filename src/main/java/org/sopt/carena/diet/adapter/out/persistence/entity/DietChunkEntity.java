package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import io.hypersistence.utils.hibernate.type.array.FloatArrayType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.sopt.carena.diet.domain.DietSection;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "diet_chunk")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietChunkEntity {

    @Id
    @Tsid
    private Long id;

    @Column(name = "chunk_id", unique = true,nullable = false)
    private String chunkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_information_id", nullable = false)
    private DietInformationEntity document;

    @Column(name = "document_id", nullable = false)
    private String documentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietSection section;

    @Column(columnDefinition = "text",nullable = false)
    private String content;

    @Column(columnDefinition = "text")
    private String embeddingText;


    @Column(name= "embedding" , columnDefinition = "vector(1536)")
    @Type(FloatArrayType.class)
    private float[] embedding;

    @Column(nullable = false)
    private int chunkOrder;

    @CreatedDate
    private LocalDateTime createdAt;

    public DietChunkEntity(
            DietInformationEntity document,
            DietSection section,
            String content,
            String embeddingText,
            float[] embedding,
            int chunkOrder
    ) {
        this.chunkId = generateChunkId(document.getDocumentId(),chunkOrder);
        this.document = document;
        this.documentId = document.getDocumentId();
        this.section = section;
        this.content = content;
        this.embeddingText = embeddingText;
        this.embedding = embedding;
        this.chunkOrder = chunkOrder;
    }

    private String generateChunkId(String documentId, int chunkOrder) {
        return String.format("CHUNK-%s-%d-%s",
                documentId,
                chunkOrder,
                UUID.randomUUID().toString().substring(0, 8)
        );
    }
}
