package org.sopt.carena.member.appliacation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.view.MemberInfoView;
import org.sopt.carena.member.appliacation.port.in.GetMemberInfoUseCase;
import org.sopt.carena.member.appliacation.port.out.MemberRepository;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService implements GetMemberInfoUseCase {

    private final MemberRepository memberRepository;

    @Override
    public MemberInfoView getMemberInfo(Long memberId, boolean withScore) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        log.info("회원 조회 완료 - name: {}, authType: {}",
                member.getName(), member.getAuthType());
        Long score = withScore ? member.getScore() : null;

        return MemberInfoView.of(
                member.getId(),
                member.getName(),
                member.getBirthdate(),
                member.getGender(),
                score
        );
    }
}
