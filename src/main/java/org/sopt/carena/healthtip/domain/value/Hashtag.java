package org.sopt.carena.healthtip.domain.value;

public record Hashtag(
		Long id,
		String name
) {
	public static Hashtag from(String name) {
		return new Hashtag(null, name);
	}

	public static Hashtag of(long id, String name) {
		return new Hashtag(id, name);
	}
}
