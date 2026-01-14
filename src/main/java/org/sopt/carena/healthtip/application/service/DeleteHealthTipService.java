package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.port.in.DeleteHealthTipUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteHealthTipService implements DeleteHealthTipUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;

	public void deleteHealthTip(final long id) {
		healthTipPersistencePort.deleteHealthTip(id);
	}
}
