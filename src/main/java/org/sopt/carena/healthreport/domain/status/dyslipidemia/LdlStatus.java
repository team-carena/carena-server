package org.sopt.carena.healthreport.domain.status.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LdlStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("LDL 수치 정상, 저밀도 콜레스테롤 수치 정상", RiskLevel.NORMAL),
	LDL_BORDERLINE("LDL 수치 다소 높음, 저밀도 콜레스테롤 수치 다소 높음, 동맥경화 위험 증가 가능, 고콜레스테롤혈증 의심", RiskLevel.BORDERLINE),
	LDL_SUSPICIOUS("LDL 수치 높음, 저밀도 콜레스테롤 수치 높음, 동맥경화 위험 증가, 고콜레스테롤혈증 의심", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static LdlStatus from(final Double ldl) {
		if(ldl == null){
			return NONE;
		}
		if(ldl<130){
			return NORMAL;
		}
		if(ldl <160){
			return LDL_BORDERLINE;
		}
		return LDL_SUSPICIOUS;
	}
}
