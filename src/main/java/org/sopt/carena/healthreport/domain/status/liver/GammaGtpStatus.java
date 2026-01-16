package org.sopt.carena.healthreport.domain.status.liver;

import org.sopt.carena.healthreport.domain.status.RiskLevel;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.member.domain.Gender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GammaGtpStatus implements HealthStatusCarrier {
	NONE("없음", RiskLevel.NONE),
	NORMAL("감마 지티피 수치 정상, GTP 수치 정상", RiskLevel.NORMAL),
	GAMMA_GTP_BORDERLINE("감마 지티피 수치 다소 높음, GTP 수치 다소 높음, 간 수치 다소 높음, 간 기능 저하", RiskLevel.BORDERLINE),
	GAMMA_GTP_SUSPECTED("감마 지티피 수치 높음, GTP 수치 높음, 간 수치 높음, 간 기능 손상", RiskLevel.SUSPECTED);

	private final String description;
	private final RiskLevel riskLevel;

	public static GammaGtpStatus of(final Double gammaGtp, final Gender gender) {
		if(gammaGtp==null){
			return GammaGtpStatus.NONE;
		}
		if(gender.equals(Gender.MALE)){
			return maleStatus(gammaGtp);
		}
		return femaleStatus(gammaGtp);
	}

	private static GammaGtpStatus maleStatus(final double gammaGtp){
		if(gammaGtp<=63){
			return NORMAL;
		}
		if(gammaGtp<=77){
			return GAMMA_GTP_BORDERLINE;
		}
		return GAMMA_GTP_SUSPECTED;
	}

	private static GammaGtpStatus femaleStatus(final double gammaGtp){
		if(gammaGtp<=35){
			return NORMAL;
		}
		if(gammaGtp<=45){
			return GAMMA_GTP_BORDERLINE;
		}
		return GAMMA_GTP_SUSPECTED;
	}
}
