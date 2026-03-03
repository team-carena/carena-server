package org.sopt.carena.institution.application.port.in;

import org.sopt.carena.institution.application.dto.view.InstitutionListView;

public interface GetInstitutionInfoUseCase {
	InstitutionListView searchInstitution(int page, Integer sidoCode, Integer sigunguCode, int type, String institutionName);
}
