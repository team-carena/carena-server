package org.sopt.carena.healthtip.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hashtag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashtagEntity {
	@Id
	@Tsid
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;


	public HashtagEntity(String name) {
		this.name = name;
	}
}
