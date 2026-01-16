package org.sopt.carena.healthreport.domain.value.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.dyslipidemia.TriglycerideStatus;

public record Triglyceride(
		Double value,
		TriglycerideStatus status
) {
	public static Triglyceride from(final Double triglyceride) {
		return new Triglyceride(triglyceride, TriglycerideStatus.from(triglyceride));
	}
}