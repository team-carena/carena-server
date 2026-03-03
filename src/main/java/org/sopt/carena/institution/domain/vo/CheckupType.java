package org.sopt.carena.institution.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CheckupType {
	GENERAL("일반검진", 1),
	ORAL("구강검진", 2),
	CANCER("암검진", 3),
	INFANT("영유아검진", 6);

	private final String name;
	private final int code;
}
