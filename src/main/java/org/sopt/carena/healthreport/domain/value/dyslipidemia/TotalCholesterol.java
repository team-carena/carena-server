package org.sopt.carena.healthreport.domain.value.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.dyslipidemia.CholesterolStatus;

public record TotalCholesterol(
		Double value,
		CholesterolStatus status
) {
	public static TotalCholesterol from(final Double totalCholesterol) {
		return new TotalCholesterol(totalCholesterol, CholesterolStatus.from(totalCholesterol));
	}
}