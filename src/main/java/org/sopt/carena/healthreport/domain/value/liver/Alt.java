package org.sopt.carena.healthreport.domain.value.liver;

import org.sopt.carena.healthreport.domain.status.liver.AltStatus;

public record Alt(
		Double value,
		AltStatus status
) {
	public static Alt from(final Double alt) {
		return new Alt(alt, AltStatus.from(alt));
	}
}