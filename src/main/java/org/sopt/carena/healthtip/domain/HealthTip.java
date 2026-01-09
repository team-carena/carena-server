package org.sopt.carena.healthtip.domain;

import java.util.List;

import org.sopt.carena.healthtip.application.dto.command.CreateHealthTipCommand;
import org.sopt.carena.healthtip.domain.value.Hashtag;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HealthTip {
	private long id;

	private String title;

	private String subTitle;

	private String content;

	private String reference;

	private List<Hashtag> hashtags;

	@Builder
	public HealthTip(long id, String title, String subTitle, String content, String reference, List<Hashtag> hashtags) {
		this.id = id;
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.reference = reference;
		this.hashtags = hashtags;
	}

	public static HealthTip create(CreateHealthTipCommand command) {
		return HealthTip.builder()
				.title(command.title())
				.subTitle(command.subTitle())
				.content(command.content())
				.reference(command.reference())
				.hashtags(normalizeHashtags(command.hashtags()))
				.build();
	}

	// 해시태그 공백 및 중복 제거
	private static List<Hashtag> normalizeHashtags(List<String> hashtags) {
		return hashtags.stream()
				.map(String::trim)
				.filter(name -> !name.isEmpty())
				.distinct()
				.map(Hashtag::from)
				.toList();
	}
}
