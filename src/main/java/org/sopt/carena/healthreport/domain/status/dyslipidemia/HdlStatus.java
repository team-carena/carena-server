package org.sopt.carena.healthreport.domain.status.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HdlStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("HDL 수치 정상, 고밀도 콜레스테롤 수치 정상", RiskLevel.NORMAL),
	HDL_BORDERLINE("HDL 수치 다소 낮음, 고밀도 콜레스테롤 수치 다소 낮음, 심혈관 보호 기능 감소 가능, 낮은 HDL 콜레스테롤 의심", RiskLevel.BORDERLINE),
	HDL_SUSPICIOUS("HDL 수치 낮음, 고밀도 콜레스테롤 수치 낮음, 심혈관 보호 기능 저하, 낮은 HDL 콜레스테롤 의심", RiskLevel.SUSPICIOUS);

	private final String description;
	private final RiskLevel riskLevel;

	public static HdlStatus from(final Double hdl) {
		if(hdl == null){
			return HdlStatus.NONE;
		}
		if(hdl >=60){
			return HdlStatus.NORMAL;
		}
		if(hdl>=40){
			return HdlStatus.HDL_BORDERLINE;
		}
		return HdlStatus.HDL_SUSPICIOUS;
	}
}
