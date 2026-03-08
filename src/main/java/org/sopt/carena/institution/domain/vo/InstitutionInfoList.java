package org.sopt.carena.institution.domain.vo;

import java.util.List;

public record InstitutionInfoList(
		List<InstitutionInfo> institutions,
		Metadata metadata
) {
	public static InstitutionInfoList of(
			final List<InstitutionInfo> institutions,
			final int currentPage,
			final int pageSize,
			final int totalCount
	) {
		return new InstitutionInfoList(
				institutions, new Metadata(currentPage, pageSize, totalCount)
		);
	}

	public record Metadata(
			int currentPage,
			int pageSize,
			int totalCount
	) {}
}
