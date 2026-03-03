package org.sopt.carena.institution.adapter.out.web.publicdata;

import java.util.List;

import org.sopt.carena.infrastructure.publicdata.client.PublicDataClient;
import org.sopt.carena.institution.adapter.out.web.publicdata.mapper.AddressCodeMapper;
import org.sopt.carena.institution.adapter.out.web.publicdata.mapper.InstitutionInfoMapper;
import org.sopt.carena.institution.application.port.out.GetAddressCodePort;
import org.sopt.carena.institution.application.port.out.GetInstitutionInfoPort;
import org.sopt.carena.institution.domain.vo.InstitutionInfoList;
import org.sopt.carena.institution.domain.vo.SidoCode;
import org.sopt.carena.institution.domain.vo.SigunguCode;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PublicDataAdapter implements GetAddressCodePort, GetInstitutionInfoPort {
	private final PublicDataClient publicDataClient;

	public List<SidoCode> getSidoCodeList() {
		return AddressCodeMapper.toSidoCodes(publicDataClient.getSidoCode());
	}

	public List<SigunguCode> getSigunguAddressCodeBySidoCode(final int sidoCode) {
		return AddressCodeMapper.toSigunguCodes(publicDataClient.getSigunguCode(sidoCode));
	}

	public InstitutionInfoList searchInstitutionInfo(
			final int page,
			final Integer sidoCode,
			final Integer sigunguCode,
			final int type,
			final String institutionName
	) {
		return InstitutionInfoMapper.toInstitutionInfoList(
				publicDataClient.getInstitutionInfo(page, sidoCode, sigunguCode, type, institutionName));
	}
}
