package org.sopt.carena.healthreport.domain.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RiskLevel {
	NONE("없음"),
	NORMAL("정상"),
	BORDERLINE("경계"),
	SUSPICIOUS("의심");

	private final String description;
}
