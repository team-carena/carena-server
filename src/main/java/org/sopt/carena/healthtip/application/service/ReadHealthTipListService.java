package org.sopt.carena.healthtip.application.service;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipListView;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipListUseCase;
import org.sopt.carena.healthtip.application.port.out.HealthTipPersistencePort;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadHealthTipListService implements ReadHealthTipListUseCase {
	private final HealthTipPersistencePort healthTipPersistencePort;
	private final MemberPersistencePort memberPersistencePort;

	public ReadHealthTipListView readHealthTipList(final String hashtagName, final int page){
		// Member member = memberPersistencePort.getMemberById(memberId).orElseThrow(MemberNotFoundException::new);

		// 20, 30, 40, 50, 60
		// member.getAge()

		return ReadHealthTipListView.from(healthTipPersistencePort.getHealthTipList(hashtagName, page));
	}
}
