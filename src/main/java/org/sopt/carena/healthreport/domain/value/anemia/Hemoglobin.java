package org.sopt.carena.healthreport.domain.value.anemia;

import org.sopt.carena.healthreport.domain.status.anemia.HemoglobinStatus;
import org.sopt.carena.member.domain.Gender;

public record Hemoglobin(
		Double value,
		HemoglobinStatus status
) {
	public static Hemoglobin of(final Double hemoglobin, final Gender gender) {
		return new Hemoglobin(hemoglobin, HemoglobinStatus.of(hemoglobin, gender));
	}
}