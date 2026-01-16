package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> recommends;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> cautionary;


    private String reference;
    private String referenceUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DietInformationEntity(
            String title,
            String content,
            List<String> recommends,
            List<String> cautionary,
            String reference,
            String referenceUrl
    ) {
        this.title = title;
        this.content = content;
        this.recommends = recommends;
        this.cautionary = cautionary;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
    }
}

