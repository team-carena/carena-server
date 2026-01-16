package org.sopt.carena.healthreport.domain.status.diabetes;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FastingGlucoseStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("공복 혈당 수치 정상", RiskLevel.NORMAL),
	IMPAIRED_FASTING_GLUCOSE("공복 혈당 수치 다소 높음, 혈당 조절 능력 저하, 공복 혈당 장애", RiskLevel.BORDERLINE),
	DIABETES_SUSPECTED("공복 혈당 수치 높음, 인슐린 분비 부족, 당뇨 의심", RiskLevel.SUSPECTED);

	private final String description;
	private final RiskLevel riskLevel;

	public static FastingGlucoseStatus from(final Double fastingGlucose) {
		if (fastingGlucose == null) {
			return NONE;
		}
		if (fastingGlucose < 100) {
			return NORMAL;
		}
		if (fastingGlucose <= 125) {
			return IMPAIRED_FASTING_GLUCOSE;
		}
		return DIABETES_SUSPECTED;
	}
}
