package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.dto.commend.CreateHealthTipCommand;
import org.sopt.carena.healthtip.application.port.in.CreateHealthTipUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateHealthTipService implements CreateHealthTipUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;

	public void createHealthTip(final CreateHealthTipCommand commend) {
		healthTipPersistencePort.saveHealthTipWithHashtags(commend);
	}
}
