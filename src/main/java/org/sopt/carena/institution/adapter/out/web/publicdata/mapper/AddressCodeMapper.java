package org.sopt.carena.institution.adapter.out.web.publicdata.mapper;

import java.util.List;

import org.sopt.carena.infrastructure.publicdata.dto.SidoCodeResponse;
import org.sopt.carena.infrastructure.publicdata.dto.SigunguCodeResponse;
import org.sopt.carena.institution.domain.vo.SidoCode;
import org.sopt.carena.institution.domain.vo.SigunguCode;
import org.sopt.carena.institution.exception.AddressCodeNotExistsException;
import org.sopt.carena.institution.exception.PublicDataAccessFailException;

public class AddressCodeMapper {
	public static List<SidoCode> toSidoCodes(SidoCodeResponse response) {
		try{
			return response.body().items().item().stream()
					.map(item -> new SidoCode(item.siDoNm(), item.siDoCd()))
					.toList();
		} catch (NullPointerException e){
			throw new PublicDataAccessFailException();
		}
	}

	public static List<SigunguCode> toSigunguCodes(SigunguCodeResponse response) {
		try {
			return response.body().items().item().stream()
					.map(item -> new SigunguCode(item.siGunGuNm(), item.siDoCd(), item.siGunGuCd()))
					.toList();
		} catch (NullPointerException e) {
			throw new AddressCodeNotExistsException();
		}
	}
}
