package org.sopt.carena.healthreport.domain.value.diabetes;

import org.sopt.carena.healthreport.domain.status.diabetes.FastingGlucoseStatus;

public record FastingGlucose(
		Double value,
		FastingGlucoseStatus status
) {
	public static FastingGlucose from(final Double fastingGlucose) {
		return new FastingGlucose(fastingGlucose, FastingGlucoseStatus.from(fastingGlucose));
	}
}