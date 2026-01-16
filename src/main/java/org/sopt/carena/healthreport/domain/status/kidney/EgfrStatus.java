package org.sopt.carena.healthreport.domain.status.kidney;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EgfrStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("신사구체 여과율 수치 정상", RiskLevel.NORMAL),
	SUSPECTED("신사구체 여과율 수치 낮음, 신장 기능 저하, 만성 신부전 의심", RiskLevel.SUSPECTED);

	private final String description;
	private final RiskLevel riskLevel;

	public static EgfrStatus from(final Double egfr) {
		if(egfr == null){
			return NONE;
		}
		if (egfr >= 60) {
			return NORMAL;
		}
		return SUSPECTED;
	}
}
