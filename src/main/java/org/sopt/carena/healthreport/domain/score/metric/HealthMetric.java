package org.sopt.carena.healthreport.domain.score.metric;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.healthreport.domain.score.ScoreResult;
import org.sopt.carena.healthreport.domain.score.rule.DeviationType;
import org.sopt.carena.member.domain.Gender;

/**
 * 각 건강 지표의 점수 계산에 필요한 메타데이터
 */
@Getter
@RequiredArgsConstructor
public enum HealthMetric {


    // 측정 지표(혈압 제외)

    BMI(new DefaultScorePolicy(
            gender -> 18.5,
            gender -> 24.9,
            gender -> 5.0,
            DeviationType.BIDIRECTIONAL)
    ),
    //허리둘레
    WAIST_CIRCUMFERENCE(new DefaultScorePolicy(
            gender -> 0.0,
            gender -> gender == Gender.MALE ? 90.0 : 85.0,
            gender -> 5.0,
            DeviationType.UPPER_ONLY)
    ),

    // 빈혈 지표 (혈색소)
    HEMOGLOBIN(new DefaultScorePolicy(
            gender -> gender == Gender.MALE ? 13.0 : 12.0,
            gender -> Double.MAX_VALUE,
            gender -> 1.0,
            DeviationType.LOWER_ONLY)
    ),

    // 당뇨 지표 (공복혈당)
    FASTING_GLUCOSE(new DefaultScorePolicy(
            gender -> 0.0,
            gender -> 100.0,
            gender -> 25.0,
            DeviationType.UPPER_ONLY)
    ),


    // 신장 지표,혈청 크리에타닌
    SERUM_CREATININE(new DefaultScorePolicy(
            gender -> 0.0,
            gender -> 1.5,
            gender -> 0.3,
            DeviationType.UPPER_ONLY)
    ),

    //신사구체여과율 ==>>>>>>  확인하기!
    EGFR(new DefaultScorePolicy(
            gender -> 60.0,
            gender -> Double.MAX_VALUE,
            gender -> 15.0,
            DeviationType.LOWER_ONLY)
    ),

    // 간 지표
    AST(new DefaultScorePolicy(
            gender -> 0.0,
            gender -> 40.0,
            gender -> 20.0,
            DeviationType.UPPER_ONLY)
    ),

    ALT(new DefaultScorePolicy(
            gender -> 0.0,
            gender -> 35.0,
            gender -> 20.0,
            DeviationType.UPPER_ONLY)
    ),

    GAMMA_GTP(new DefaultScorePolicy(
            gender -> gender == Gender.MALE? 11.0 : 8.0,
            gender -> gender == Gender.MALE ? 63.0 : 35.0,
            gender -> gender == Gender.MALE ? 50.0 : 30.0,
            DeviationType.UPPER_ONLY)
    );


    private final ScorePolicy scorePolicy;

    public ScoreResult calculateResult(Double value, Gender gender) {
        return scorePolicy.calculate(value, gender);
    }
}
