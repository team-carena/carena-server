package org.sopt.carena.healthreport.domain.status.liver;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AstStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("아스파테이트아미노전달효소 수치 정상, AST 수치 정상", RiskLevel.NORMAL),
	AST_BORDERLINE("아스파테이트아미노전달효소 수치 다소 높음, AST 수치 다소 높음, 간 수치 다소 높음, 간 기능 저하", RiskLevel.BORDERLINE),
	AST_SUSPICIOUS("아스파테이트아미노전달효소 수치 높음, AST 수치 높음, 간 수치 높음, 간 기능 손상, 간염 의심, 간 경변 의심", RiskLevel.SUSPICIOUS);


	private final String description;
	private final RiskLevel riskLevel;

	public static AstStatus from(final Double ast) {
		if(ast == null) {
			return AstStatus.NONE;
		}
		if(ast <= 40){
			return NORMAL;
		}
		if(ast <= 50){
			return AST_BORDERLINE;
		}
		return AST_SUSPICIOUS;
	}
}
