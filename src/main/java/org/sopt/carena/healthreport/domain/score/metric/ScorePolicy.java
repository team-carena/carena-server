package org.sopt.carena.healthreport.domain.score.metric;

//계산 전략 인터페이스
import org.sopt.carena.healthreport.domain.score.ScoreResult;
import org.sopt.carena.member.domain.Gender;

public interface ScorePolicy {
    ScoreResult calculate(Double value, Gender gender);
}