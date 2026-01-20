package org.sopt.carena.healthreport.domain.status.kidney;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SerumCreatinineStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	SERUM_CREATININE_NORMAL("혈청 크레아티닌 수치 정상", RiskLevel.NORMAL),
	SERUM_CREATININE_SUSPICIOUS("혈청 크레아티닌 수치 높음, 크레아티닌 배설이 원활하지 않음, 신장 기능 저하", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static SerumCreatinineStatus from(final Double serumCreatinine) {
		if(serumCreatinine == null){
			return NONE;
		}
		if (serumCreatinine <= 1.5) {
			return SERUM_CREATININE_NORMAL;
		}
		return SERUM_CREATININE_SUSPICIOUS;
	}
}
