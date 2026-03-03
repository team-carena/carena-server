package org.sopt.carena.institution.application.service;

import org.sopt.carena.institution.application.dto.view.InstitutionListView;
import org.sopt.carena.institution.application.port.in.GetInstitutionInfoUseCase;
import org.sopt.carena.institution.application.port.out.GetInstitutionInfoPort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetInstitutionInfoService implements GetInstitutionInfoUseCase {
	private final GetInstitutionInfoPort getInstitutionInfoPort;

	public InstitutionListView searchInstitution(
			final int page,
			final Integer sidoCode,
			final Integer sigunguCode,
			final int type,
			final String institutionName
	) {
		return InstitutionListView.from(
				getInstitutionInfoPort.searchInstitutionInfo(page, sidoCode, sigunguCode, type, institutionName));
	}
}
