package org.sopt.carena.institution.application.dto.view;

import java.util.List;

import org.sopt.carena.institution.domain.vo.InstitutionInfo;
import org.sopt.carena.institution.domain.vo.InstitutionInfoList;

public record InstitutionListView(
		List<InstitutionInfo> result,
		int currentPage,
		int totalPage,
		int totalCount
) {
	public static InstitutionListView from(final InstitutionInfoList institutionInfoList) {
		int totalCount = institutionInfoList.metadata().totalCount();
		int pageSize = institutionInfoList.metadata().pageSize();
		int totalPage = (int) Math.ceil((double) totalCount / pageSize);

		return new InstitutionListView(
				institutionInfoList.institutions(),
				institutionInfoList.metadata().currentPage(),
				totalPage,
				totalCount
		);
	}
}
