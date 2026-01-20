package org.sopt.carena.healthreport.domain.status.measurement;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.member.domain.Gender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaistCircumferenceStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("허리둘레 수치 정상", RiskLevel.NORMAL),
	ABDOMINAL_OBESITY("복부 비만", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static WaistCircumferenceStatus of(final Double waistCircumference, final Gender gender) {
		if(waistCircumference == null) {
			return NONE;
		}
		if(gender.equals(Gender.MALE)) {
			return maleStatus(waistCircumference);
		}
		return femaleStatus(waistCircumference);
	}

	private static WaistCircumferenceStatus maleStatus(final double waistCircumference) {
		if(waistCircumference >= 90) {
			return ABDOMINAL_OBESITY;
		}
		return NORMAL;
	}

	private static WaistCircumferenceStatus femaleStatus(final double waistCircumference) {
		if(waistCircumference >= 85) {
			return ABDOMINAL_OBESITY;
		}
		return NORMAL;
	}
}
