package org.sopt.carena.institution.application.port.out;

import org.sopt.carena.institution.domain.vo.InstitutionInfoList;

public interface GetInstitutionInfoPort {
	InstitutionInfoList searchInstitutionInfo(int page, Integer sidoCode, Integer sigunguCode, int type, String institutionName);
}
