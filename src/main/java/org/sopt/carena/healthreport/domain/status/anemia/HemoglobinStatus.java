package org.sopt.carena.healthreport.domain.status.anemia;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.member.domain.Gender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HemoglobinStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("혈색소 수치 정상", RiskLevel.NORMAL),
	ANEMIA_BORDERLINE("혈색소 수치 다소 낮음, 철분 부족, 헤모글로빈 부족, 빈혈 의심", RiskLevel.BORDERLINE),
	ANEMIA_SUSPECTED("혈색소 수치 낮음, 철분 부족, 헤모글로빈 부족, 빈혈 의심", RiskLevel.SUSPECTED),
	POLYCYTHEMIA_SUSPECTED("혈색소 수치 높음, 헤모글로빈 과다, 적혈구 과다증 의심", RiskLevel.SUSPECTED);

	private final String description;
	private final RiskLevel riskLevel;

	public static HemoglobinStatus of(final Double hemoglobin, final Gender gender) {
		if(hemoglobin == null){
			return NONE;
		}
		if(gender.equals(Gender.MALE)){
			return maleStatus(hemoglobin);
		}
		return femaleStatus(hemoglobin);
	}

	private static HemoglobinStatus maleStatus(final double hemoglobin) {
		if(hemoglobin >= 16.6){
			return POLYCYTHEMIA_SUSPECTED;
		}
		if(hemoglobin >= 13){
			return NORMAL;
		}
		if(hemoglobin > 12){
			return ANEMIA_BORDERLINE;
		}
		return ANEMIA_SUSPECTED;
	}

	private static HemoglobinStatus femaleStatus(final double hemoglobin) {
		if(hemoglobin >= 15){
			return POLYCYTHEMIA_SUSPECTED;
		}
		if(hemoglobin >= 12){
			return NORMAL;
		}
		if(hemoglobin > 10){
			return ANEMIA_BORDERLINE;
		}
		return ANEMIA_SUSPECTED;
	}
}
