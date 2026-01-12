package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipDetailView;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipDetailUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.sopt.carena.healthtip.exception.HealthTipNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadHealthTipDetailService implements ReadHealthTipDetailUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;

	public ReadHealthTipDetailView readHealthTipDetail(final long id) {
		return ReadHealthTipDetailView.from(
				healthTipPersistencePort.getHealthTipDetail(id).orElseThrow(HealthTipNotFoundException::new));
	}
}
