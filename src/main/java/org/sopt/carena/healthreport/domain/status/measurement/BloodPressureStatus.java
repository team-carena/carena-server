package org.sopt.carena.healthreport.domain.status.measurement;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BloodPressureStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("혈압 정상", RiskLevel.NORMAL),
	PRE_HYPERTENSION("고혈압 전단계", RiskLevel.BORDERLINE),
	HYPERTENSION("고혈압 의심", RiskLevel.SUSPECTED);

	private final String description;
	private final RiskLevel riskLevel;

	/**
	 * 판별 기준:e
	 * - 고혈압 의심: 수축기 >= 140 OR 이완기 >= 90
	 * - 고혈압 전단계: 수축기 120~139 OR 이완기 80~89
	 * - 정상: 그 외
	 */
	public static BloodPressureStatus of(final Integer systolicBp, final Integer diastolicBp) {
		if (systolicBp == null || diastolicBp == null) {
			return NONE;
		}
		if (systolicBp >= 140 || diastolicBp >= 90) {
			return BloodPressureStatus.HYPERTENSION;
		}

		if (systolicBp >= 120 || diastolicBp >= 80) {
			return BloodPressureStatus.PRE_HYPERTENSION;
		}

		return BloodPressureStatus.NORMAL;
	}

	public static BloodPressureStatus ofSystolicBp(final Integer systolicBp) {
		if (systolicBp == null) {
			return NONE;
		}
		if (systolicBp >= 140) {
			return BloodPressureStatus.HYPERTENSION;
		}

		if (systolicBp >= 120) {
			return BloodPressureStatus.PRE_HYPERTENSION;
		}

		return BloodPressureStatus.NORMAL;
	}

	public static BloodPressureStatus ofDiastolicBp(final Integer diastolicBp) {
		if (diastolicBp == null) {
			return NONE;
		}
		if (diastolicBp >= 90) {
			return BloodPressureStatus.HYPERTENSION;
		}

		if (diastolicBp >= 80) {
			return BloodPressureStatus.PRE_HYPERTENSION;
		}

		return BloodPressureStatus.NORMAL;
	}
}
