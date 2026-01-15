package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "diet_information")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietInformationEntity {

    @Id
    @Tsid
    private Long id;

    @Column(name = "document_id", unique = true)
    private String documentId;

    @Column(nullable = false)
    private String title;

    private String reference;
    private String referenceUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DietInformationEntity(
            String title,
            String reference,
            String referenceUrl
    ) {
        this.documentId = generateDocumentId();
        this.title = title;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
    }

    private String generateDocumentId() {
        return "DOC-" + System.currentTimeMillis();
    }
}

