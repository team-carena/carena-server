package org.sopt.carena.healthreport.domain.status.liver;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AltStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("알라닌아미노전달효소 수치 정상, ALT 수치 정상", RiskLevel.NORMAL),
	ALT_BORDERLINE("알라닌아미노전달효소 다소 수치 높음, ALT 수치 다소 높음, 간 수치 다소 높음, 간 기능 저하", RiskLevel.BORDERLINE),
	ALT_SUSPICIOUS("알라닌아미노전달효소 수치 높음, ALT 수치 높음, 간 수치 높음, 간 기능 손상, 간염 의심, 지방간 의심", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static AltStatus from(final Double alt){
		if(alt == null){
			return NONE;
		}
		if(alt <=35){
			return NORMAL;
		}
		if(alt <=45){
			return ALT_BORDERLINE;
		}
		return ALT_SUSPICIOUS;
	}
}
