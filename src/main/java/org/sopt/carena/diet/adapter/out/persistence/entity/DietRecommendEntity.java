package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "diet_recommend", indexes = {
        @Index(name = "idx_diet_recommend_member_id", columnList = "member_id"),
        @Index(name = "idx_diet_recommend_health_report_id", columnList = "health_report_id"),
        @Index(name = "idx_diet_recommend_diet_information_id", columnList = "diet_information_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietRecommendEntity {

    @Id
    @Tsid
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "health_report_id", nullable = false)
    private Long healthReportId;

    @Column(name = "diet_information_id", nullable = false)
    private Long dietInformationId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "similarity_score")
    private Double similarityScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private DietRecommendEntity(
            Long id,
            Long memberId,
            Long healthReportId,
            Long dietInformationId,
            String title,
            String content,
            Double similarityScore,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.healthReportId = healthReportId;
        this.dietInformationId = dietInformationId;
        this.title = title;
        this.content = content;
        this.similarityScore = similarityScore;
        this.createdAt = createdAt;
    }
}
