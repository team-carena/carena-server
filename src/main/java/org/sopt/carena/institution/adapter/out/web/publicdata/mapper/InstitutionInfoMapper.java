package org.sopt.carena.institution.adapter.out.web.publicdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.sopt.carena.infrastructure.publicdata.dto.InstitutionInfoResponse;
import org.sopt.carena.institution.domain.vo.CheckupType;
import org.sopt.carena.institution.domain.vo.InstitutionInfo;
import org.sopt.carena.institution.domain.vo.InstitutionInfoList;

public class InstitutionInfoMapper {
	public static InstitutionInfoList toInstitutionInfoList(InstitutionInfoResponse response) {
		try{
			List<InstitutionInfo> institutions = response.body().items().item().stream()
					.map(item -> new InstitutionInfo(item.hmcNm(), item.locAddr(), item.cyVl(), item.cxVl(),
							resolveCheckupTypes(item)))
					.toList();

			return InstitutionInfoList.of(institutions,
					response.body().pageNo(),
					response.body().numOfRows(),
					response.body().totalCount());
		} catch (NullPointerException e) {
			return InstitutionInfoList.of(List.of(), 0, 0, 0);
		}
	}

	private static List<String> resolveCheckupTypes(InstitutionInfoResponse.Item item) {
		List<String> types = new ArrayList<>();

		if (item.grenChrgTypeCd() == 1)
			types.add(CheckupType.GENERAL.getName());
		if (item.mchkChrgTypeCd() == 1)
			types.add(CheckupType.ORAL.getName());
		if (item.ichkChrgTypeCd() == 1)
			types.add(CheckupType.INFANT.getName());
		if (item.bcExmdChrgTypeCd() == 1
				|| item.ccExmdChrgTypeCd() == 1
				|| item.cvxcaExmdChrgTypeCd() == 1
				|| item.lvcaExmdChrgTypeCd() == 1
				|| item.stmcaExmdChrgTypeCd() == 1) {
			types.add(CheckupType.CANCER.getName());
		}

		return types;
	}
}
