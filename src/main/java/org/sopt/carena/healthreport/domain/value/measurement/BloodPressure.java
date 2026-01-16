package org.sopt.carena.healthreport.domain.value.measurement;

import org.sopt.carena.healthreport.domain.status.measurement.BloodPressureStatus;

public record BloodPressure(
		Integer systolicBp,    // 수축기 혈압
		Integer diastolicBp ,   // 이완기 혈압
		BloodPressureStatus status,
		BloodPressureStatus systolicBpStatus,
		BloodPressureStatus diastolicBpStatus
) {
	public static BloodPressure of(final Integer systolicBp, final Integer diastolicBp) {
		return new BloodPressure(
				systolicBp,
				diastolicBp,
				BloodPressureStatus.of(systolicBp, diastolicBp),
				BloodPressureStatus.ofSystolicBp(systolicBp),
				BloodPressureStatus.ofDiastolicBp(diastolicBp));
	}
}
