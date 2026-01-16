package org.sopt.carena.healthreport.domain.value.liver;

import org.sopt.carena.healthreport.domain.status.liver.GammaGtpStatus;
import org.sopt.carena.member.domain.Gender;

public record GammaGtp(
		Double value,
		GammaGtpStatus status
) {
	public static GammaGtp of(final Double gammaGtp, final Gender gender) {
		return new GammaGtp(gammaGtp, GammaGtpStatus.of(gammaGtp, gender));
	}
}