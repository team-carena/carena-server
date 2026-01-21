package org.sopt.carena.healthreport.domain.status.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CholesterolStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("총 콜레스테롤 수치 정상", RiskLevel.NORMAL),
	CHOLESTEROL_BORDERLINE("총 콜레스테롤 다소 수치 높음, 지질 대사 이상 가능성, 고콜레스테롤혈증 의심", RiskLevel.BORDERLINE),
	CHOLESTEROL_SUSPICIOUS("총 콜레스테롤 수치 높음, LDL 증가 가능, 지질 대사 이상, 고콜레스테롤혈증 의심", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static CholesterolStatus from(final Double cholesterol) {
		if(cholesterol == null){
			return NONE;
		}
		if(cholesterol<200){
			return NORMAL;
		}
		if(cholesterol<240){
			return CHOLESTEROL_BORDERLINE;
		}
		return CHOLESTEROL_SUSPICIOUS;
	}
}
