package org.sopt.carena.healthreport.domain.value.measurement;

import org.sopt.carena.healthreport.domain.status.measurement.BmiStatus;

public record Bmi(
		Double value,
		BmiStatus status
) {
	public static Bmi from(final Double bmi) {
		return new Bmi(bmi, BmiStatus.from(bmi));
	}
}
