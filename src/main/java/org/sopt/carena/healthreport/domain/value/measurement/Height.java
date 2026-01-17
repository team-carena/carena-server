package org.sopt.carena.healthreport.domain.value.measurement;

public record Height(
		Double value
) {
	public static Height from(final Double height) {
		return new Height(height);
	}
}
