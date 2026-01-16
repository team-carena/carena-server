package org.sopt.carena.healthreport.domain.value.measurement;

public record Weight(
		Double value
) {
	public static Weight from(final Double weight) {
		return new Weight(weight);
	}
}
