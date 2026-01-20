package org.sopt.carena.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.application.dto.view.MemberInfoView;
import org.sopt.carena.member.application.dto.view.MyPageInfoView;
import org.sopt.carena.member.application.port.in.GetMemberInfoUseCase;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.sopt.carena.member.exception.jwt.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService implements GetMemberInfoUseCase {

    private final MemberPersistencePort memberRepository;
    private final HealthReportPersistencePort healthReportRepository;

    @Override
    public MemberInfoView getMemberInfo(final Long memberId) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Optional<HealthReport> healthReport = healthReportRepository.findLatestByMemberId(memberId);
        LocalDate latestHealthCheckDate = healthReport
                .map(HealthReport::getHealthCheckDate)
                .orElse(null);


        return MemberInfoView.of(
                member.getName(),
                member.getBirthdate(),
                member.getGender(),
                member.getScore(),
                latestHealthCheckDate
        );
    }
    @Override
    public MyPageInfoView getMyPageInfo(final Long memberId) {
        Member member = memberRepository.getMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return MyPageInfoView.of(
                member.getName(),
                member.getBirthdate()
        );
    }
}
