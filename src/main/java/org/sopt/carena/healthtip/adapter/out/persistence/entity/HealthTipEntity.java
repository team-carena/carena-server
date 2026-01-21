package org.sopt.carena.healthtip.adapter.out.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.carena.global.common.BaseEntity;

@Entity
@Getter
@Table(name = "health_tip")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthTipEntity extends BaseEntity {
	@Id
	@Tsid
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "subtitle", nullable = false)
	private String subTitle;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "reference", nullable = false)
	private String reference;

	@OneToMany(mappedBy = "healthTip", fetch = FetchType.LAZY)
	List<HealthTipHashtagEntity> hashtags = new ArrayList<>();

	@Builder
	public HealthTipEntity(String title, String subTitle, String content, String reference) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.reference = reference;
	}
}
