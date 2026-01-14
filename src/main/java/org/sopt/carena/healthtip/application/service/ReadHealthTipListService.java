package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipListView;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipListUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadHealthTipListService implements ReadHealthTipListUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;

	public ReadHealthTipListView readHealthTipList(final int page){
		return ReadHealthTipListView.from(healthTipPersistencePort.getHealthTipList(page));
	}
}
