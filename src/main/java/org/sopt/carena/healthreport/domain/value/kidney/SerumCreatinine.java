package org.sopt.carena.healthreport.domain.value.kidney;

import org.sopt.carena.healthreport.domain.status.kidney.SerumCreatinineStatus;

public record SerumCreatinine(
		Double value,
		SerumCreatinineStatus status
) {
	public static SerumCreatinine from(final Double serumCreatinine) {
		return new SerumCreatinine(serumCreatinine, SerumCreatinineStatus.from(serumCreatinine));
	}
}
