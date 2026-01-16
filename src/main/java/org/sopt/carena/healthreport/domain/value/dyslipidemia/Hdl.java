package org.sopt.carena.healthreport.domain.value.dyslipidemia;

import org.sopt.carena.healthreport.domain.status.dyslipidemia.HdlStatus;

public record Hdl(
		Double value,
		HdlStatus status
) {
	public static Hdl from(final Double hdl) {
		return new Hdl(hdl, HdlStatus.from(hdl));
	}
}