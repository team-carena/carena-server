package org.sopt.carena.institution.application.service;

import org.sopt.carena.institution.application.dto.view.SidoCodeView;
import org.sopt.carena.institution.application.dto.view.SigunguCodeView;
import org.sopt.carena.institution.application.port.in.GetAddressCodeUseCase;
import org.sopt.carena.institution.application.port.out.GetAddressCodePort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAddressCodeService implements GetAddressCodeUseCase {
	private final GetAddressCodePort getAddressCodePort;

	public SidoCodeView getSidoCodes() {
		return SidoCodeView.from(getAddressCodePort.getSidoCodeList());
	}

	public SigunguCodeView getSigunguCodes(final int sidoCode) {
		return SigunguCodeView.from(getAddressCodePort.getSigunguAddressCodeBySidoCode(sidoCode));
	}
}
