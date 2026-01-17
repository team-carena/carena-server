package org.sopt.carena.healthreport.domain.value.kidney;

import org.sopt.carena.healthreport.domain.status.kidney.EgfrStatus;

public record Egfr(
		Double value,
		EgfrStatus status
) {
	public static Egfr from(final Double egfr) {
		return new Egfr(egfr, EgfrStatus.from(egfr));
	}
}