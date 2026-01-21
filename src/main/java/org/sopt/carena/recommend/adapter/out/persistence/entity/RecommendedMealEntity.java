package org.sopt.carena.recommend.adapter.out.persistence.entity;

import org.sopt.carena.global.common.BaseEntity;
import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "recommended_meal",
		indexes = {
				@Index(name = "idx_recommended_meal_member_id", columnList = "member_id"),
				@Index(name = "idx_recommended_meal_health_report_id", columnList = "health_report_id")
		})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedMealEntity extends BaseEntity {
	@Id
	@Tsid
	private Long id;

	@Column(name = "meal", nullable = false, updatable = false)
	private String meal;

	@Column(name = "description", nullable = false, updatable = false)
	private String description;

	@Column(name = "base_document_id", nullable = false, updatable = false)
	private Long baseDocumentId;

	@Column(name = "base_document_title", nullable = false, updatable = false)
	private String baseDocumentTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity memberEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "health_report_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private HealthReportEntity healthReportEntity;

	@Builder
	private RecommendedMealEntity(
			final String meal,
			final String description,
			final Long baseDocumentId,
			final String baseDocumentTitle,
			final MemberEntity memberEntity,
			final HealthReportEntity healthReportEntity
	) {
		this.meal = meal;
		this.description = description;
		this.baseDocumentId = baseDocumentId;
		this.baseDocumentTitle = baseDocumentTitle;
		this.memberEntity = memberEntity;
		this.healthReportEntity = healthReportEntity;
	}
}
