package org.sopt.carena.member.application.port.in;

import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.domain.Member;

public interface HealthScoreUseCase {
    void updateMemberScore(Member member, HealthReport healthReport);
}
