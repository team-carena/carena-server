package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.application.dto.view.MemberInfoView;
import org.sopt.carena.member.application.dto.view.MyPageInfoView;
import org.sopt.carena.member.application.port.in.GetMemberInfoUseCase;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService implements GetMemberInfoUseCase {

    private final MemberPersistencePort memberRepository;

    @Override
    public MemberInfoView getMemberInfo(Long memberId) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return MemberInfoView.of(
                member.getName(),
                member.getAge(),
                member.getGender(),
                member.getScore()
        );
    }
    @Override
    public MyPageInfoView getMyPageInfo(Long memberId) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return MyPageInfoView.of(
                member.getName(),
                member.getBirthdate()
        );
    }
}
