package org.sopt.carena.healthreport.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.score.HealthScore;
import org.sopt.carena.healthreport.domain.score.caculator.HealthScoreCalculator;
import org.sopt.carena.member.application.port.out.MemberPersistencePort;
import org.sopt.carena.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberHealthScoreService {

    private final HealthScoreCalculator healthScoreCalculator;
    private final MemberPersistencePort memberPersistencePort;

    public void updateMemberScore(Member member, HealthReport healthReport) {
        HealthScore score = healthScoreCalculator.calculate(healthReport);

        member.updateScore(score.getValue());
        memberPersistencePort.updateScore(member.getId(), score.getValue());

        //memberPersistencePort.save(member);
    }
}