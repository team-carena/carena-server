package org.sopt.carena.healthreport.domain.value.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.dyslipidemia.LdlStatus;

public record Ldl(
		Double value,
		LdlStatus status
) {
	public static Ldl from(final Double ldl) {
		return new Ldl(ldl, LdlStatus.from(ldl));
	}
}