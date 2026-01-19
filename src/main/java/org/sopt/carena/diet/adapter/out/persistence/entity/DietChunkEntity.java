package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.type.SqlTypes;
import org.sopt.carena.diet.domain.value.DietSection;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "diet_chunk")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietChunkEntity {

    @Id
    @Tsid
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_information_id", nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DietInformationEntity document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietSection section;

    @Column(columnDefinition = "text",nullable = false)
    private String content;

    @Column(columnDefinition = "text")
    private String embeddingText;

    @Column(name= "embedding" , columnDefinition = "vector(1536)")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Basic(fetch = FetchType.LAZY)
    private float[] embedding;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

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
            int chunkOrder,
            Map<String, Object> metadata
    ) {
        this.document = document;
        this.section = section;
        this.content = content;
        this.embeddingText = embeddingText;
        this.embedding = embedding;
        this.chunkOrder = chunkOrder;
        this.metadata = metadata;
    }
}
