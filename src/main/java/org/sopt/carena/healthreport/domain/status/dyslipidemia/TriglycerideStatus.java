package org.sopt.carena.healthreport.domain.status.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TriglycerideStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("중성지방 수치 정상", RiskLevel.NORMAL),
	TRIGLYCERIDE_BORDERLINE("중성지방 수치 높음, 지방 대사 이상 가능성, 고중성지방혈증 의심", RiskLevel.BORDERLINE),
	TRIGLYCERIDE_SUSPICIOUS("중성지방 수치 높음, 지방 대사 이상, 고중성지방혈증 의심", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static TriglycerideStatus from(final Double triglyceride) {
		if(triglyceride == null){
			return NONE;
		}
		if(triglyceride<150){
			return NORMAL;
		}
		if(triglyceride <200){
			return TRIGLYCERIDE_BORDERLINE;
		}
		return TRIGLYCERIDE_SUSPICIOUS;
	}
}
