package org.sopt.carena.institution.application.port.out;

import java.util.List;

import org.sopt.carena.institution.domain.vo.SidoCode;
import org.sopt.carena.institution.domain.vo.SigunguCode;

public interface GetAddressCodePort {
	List<SidoCode> getSidoCodeList();

	List<SigunguCode> getSigunguAddressCodeBySidoCode(int sidoCode);
}
