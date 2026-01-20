package org.sopt.carena.healthreport.domain.status.measurement;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BmiStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	UNDERWEIGHT("저체중, 체중 증량 필요", RiskLevel.BORDERLINE),
	NORMAL("체질량지수 정상", RiskLevel.NORMAL),
	OVERWEIGHT("과체중, 체중 감소 필요", RiskLevel.BORDERLINE),
	OBESE("비만, 체중 감소 필요", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static BmiStatus from(final Double bmi) {
		if(bmi == null) {
			return NONE;
		}
		if (bmi < 18.5) {
			return UNDERWEIGHT;
		}
		if (bmi < 25.0) {
			return NORMAL;
		}
		if (bmi < 30.0) {
			return OVERWEIGHT;
		}
		return OBESE;
	}
}
