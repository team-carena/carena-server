package org.sopt.carena.healthreport.domain.value.measurement;

import org.sopt.carena.healthreport.domain.status.measurement.WaistCircumferenceStatus;
import org.sopt.carena.member.domain.Gender;

public record WaistCircumference(
		Double value,
		WaistCircumferenceStatus status
) {
	public static WaistCircumference of(final Double waistCircumference, final Gender gender) {
		return new WaistCircumference(waistCircumference, WaistCircumferenceStatus.of(waistCircumference, gender));
	}
}
