package org.sopt.carena.institution.domain.vo;

import java.util.List;

public record InstitutionInfo(
		String institutionName,
		String institutionAddress,
		double latitude,
		double longitude,
		List<String> types
) {}