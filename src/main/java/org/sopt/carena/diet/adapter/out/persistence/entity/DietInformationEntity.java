package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_information")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietInformationEntity {

    @Id
    @Tsid
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "document")
    private List<DietChunkEntity> chunks = new ArrayList<>();


    @OneToOne(mappedBy = "dietInformation",cascade = CascadeType.ALL)
    private RecommendedCategoryEntity recommendedFood;

    @OneToOne(mappedBy = "dietInformation",cascade = CascadeType.ALL)
    private CautionCategoryEntity cautionaryFood;

    private String reference;
    private String referenceUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DietInformationEntity(
            String title,
            String content,
            String reference,
            String referenceUrl
    ) {
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
    }
    public void setRecommendedFood(RecommendedCategoryEntity recommendedFood) {
        this.recommendedFood = recommendedFood;
    }

    public void setCautionaryFood(CautionCategoryEntity cautionaryFood) {
        this.cautionaryFood = cautionaryFood;
    }

    public void addChunk(DietChunkEntity chunk) {
        this.chunks.add(chunk);
    }

    public void addAllChunks(List<DietChunkEntity> chunks) {
        this.chunks.addAll(chunks);
    }
}

