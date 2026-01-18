package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipTickerView;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipTickerUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadHealthTipTickerService implements ReadHealthTipTickerUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;
	private final MemberPersistencePort memberPersistencePort;

	public ReadHealthTipTickerView readHealthTipTicker(final long memberId) {
		Member member = memberPersistencePort.getMemberById(memberId).orElseThrow(MemberNotFoundException::new);

		return ReadHealthTipTickerView.from(healthTipPersistencePort.getHealthTipTicker(getAgeGroup(member.getAge())));
	}

	private String getAgeGroup(final int age){
		int normalizedAge = Math.min(Math.max(age, 20), 60);
		int ageGroup = (normalizedAge / 10) * 10;
		return ageGroup + "대";
	}
}
