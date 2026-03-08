package org.sopt.carena.institution.application.port.in;

import org.sopt.carena.institution.application.dto.view.SidoCodeView;
import org.sopt.carena.institution.application.dto.view.SigunguCodeView;

public interface GetAddressCodeUseCase {
	SidoCodeView getSidoCodes();

	SigunguCodeView getSigunguCodes(int sidoCode);
}
