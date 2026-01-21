package org.sopt.carena.healthreport.adapter.out.persistence.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.sopt.carena.global.common.BaseEntity;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "health_report_embedding",
		indexes = {
				@Index(name = "idx_health_report_embedding_member_id", columnList = "member_id"),
				@Index(name = "idx_health_report_embedding_health_report_id", columnList = "health_report_id")
		})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthReportEmbeddingEntity extends BaseEntity {
	@Id
	@Tsid
	private Long id;

	@Column(name = "embedding_text", columnDefinition = "TEXT")
	private String embeddingText;

	@Column(name = "embedding", columnDefinition = "vector(1536)")
	@JdbcTypeCode(SqlTypes.VECTOR)
	private float[] embedding;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "health_report_id", unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private HealthReportEntity healthReportEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity memberEntity;

	@Builder
	private HealthReportEmbeddingEntity(
			String embeddingText,
			float[] embedding,
			HealthReportEntity healthReportEntity,
			MemberEntity memberEntity
	) {
		this.embeddingText = embeddingText;
		this.embedding = embedding;
		this.healthReportEntity = healthReportEntity;
		this.memberEntity = memberEntity;
	}
}
