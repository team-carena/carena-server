package org.sopt.carena.healthtip.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "health_tip_hashtag",
		indexes = {
				@Index(name = "idx_health_tip_hashtag_health_tip_id", columnList = "health_tip_id"),
				@Index(name = "idx_health_tip_hashtag_hashtag_id", columnList = "hashtag_id")
		})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthTipHashtagEntity {
	@Id
	@Tsid
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "health_tip_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private HealthTipEntity healthTip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private HashtagEntity hashtag;

	public HealthTipHashtagEntity(HealthTipEntity healthTip, HashtagEntity hashtag) {
		this.healthTip = healthTip;
		this.hashtag = hashtag;
	}
}
