package org.sopt.carena.healthreport.domain.score.caculator;

import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.score.HealthScore;
import org.sopt.carena.healthreport.domain.score.ScoreResult;
import org.sopt.carena.healthreport.domain.score.caculator.metric.BloodPressureScorePolicy;
import org.sopt.carena.healthreport.domain.score.caculator.metric.HealthMetric;
import org.sopt.carena.member.domain.Gender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 건강 점수 계산 도메인 서비스
 */
@Component
@Slf4j
public class HealthScoreCalculator {
    private final BloodPressureScorePolicy bloodPressureScorePolicy
            = new BloodPressureScorePolicy();


    public HealthScore calculate(HealthReport healthReport) {


        Gender gender = healthReport.getGender();

        // 각 항목별 점수 계산
        //체질량지수
        ScoreResult bmiScore = HealthMetric.BMI.calculateResult(
                healthReport.getBmi().value(), gender);

        //  혈압은 특수 계산 SYSTOLIC_BP
        ScoreResult bpScore = bloodPressureScorePolicy.calculate(
                healthReport.getBloodPressure().systolicBp(),
                healthReport.getBloodPressure().diastolicBp()
        );

        //허리둘레
        ScoreResult waistScore = HealthMetric.WAIST_CIRCUMFERENCE.calculateResult(
                healthReport.getWaistCircumference().value(), gender);

        //혈색소
        ScoreResult hemoglobinScore = HealthMetric.HEMOGLOBIN.calculateResult(
                healthReport.getHemoglobin().value(), gender);


        //공복혈당
        ScoreResult fastingGlucoseScore = HealthMetric.FASTING_GLUCOSE.calculateResult(
                healthReport.getFastingGlucose().value(), gender);


        //혈청크레아티닌
        ScoreResult serumCreatinineScore = HealthMetric.SERUM_CREATININE.calculateResult(
                healthReport.getSerumCreatinine().value(), gender);

        //신사구체여과율
        ScoreResult egfrScore = HealthMetric.EGFR.calculateResult(
                healthReport.getEgfr().value(), gender);

        //AST
        ScoreResult astScore = HealthMetric.AST.calculateResult(
                healthReport.getAst().value(), gender);

        //ALT
        ScoreResult altScore = HealthMetric.ALT.calculateResult(
                healthReport.getAlt().value(), gender);

        //감마지티피
        ScoreResult gammaGtpScore = HealthMetric.GAMMA_GTP.calculateResult(
                healthReport.getGammaGtp().value(), gender);



               /*
        - 비만도 점수 = (BMI 단계 점수 + 허리둘레 단계 점수) / 2
    - 둘 다 2단계 이상 → -10점 추가 감점
    - 둘 다 3단계 이상 → -20점 추가 감점
- 혈압 점수 = (수축기 단계 점수 + 이완기 단계 점수) / 2
    - 둘 다 2단계 이상 → -10점
    - 하나라도 3단계 이상 → -20점
- 혈당 점수 = 공복혈당 단계 점수
- 신장 점수 = (크레아티닌 단계 점수 + 신사구체여과율 단계 점수) /2
    - 둘 다 2단계 이상 → -15점
    - 하나라도 3단계 이상 → -25점
- 간 기능 점수 = (AST + ALT + GGT 단계 점수) / 3
    - 2개 이상 2단계 이상 → -10점
    - 3개 모두 3단계 이상 → -20점
- 혈액 점수 = 혈색소 단계 점수
         */

        //비만도점수
        int obesityScore = (bmiScore.getScore() +waistScore.getScore()) / 2;

        if (bmiScore.isAtLeast(3) && waistScore.isAtLeast(3)) {
            obesityScore -= 20;
        } else if (bmiScore.isAtLeast(2) && waistScore.isAtLeast(2)) {
            obesityScore -= 10;
        }

        //신장
        int kidneysScore = (serumCreatinineScore.getScore() +egfrScore.getScore()) / 2;

        if (serumCreatinineScore.isAtLeast(3) || egfrScore.isAtLeast(3)) {
            kidneysScore -= 25;
        } else if (serumCreatinineScore.isAtLeast(2) && egfrScore.isAtLeast(2)) {
            kidneysScore -= 15;
        }


        //간기능
        int liverScore = (astScore.getScore() +altScore.getScore()+ gammaGtpScore.getScore()) / 3;

        if (astScore.isAtLeast(3)
                && altScore.isAtLeast(3)
                && gammaGtpScore.isAtLeast(3)) {

            liverScore -= 20;
        } else if (
                (astScore.isAtLeast(2) && altScore.isAtLeast(2))
                        || (astScore.isAtLeast(2) && gammaGtpScore.isAtLeast(2))
                        || (altScore.isAtLeast(2) && gammaGtpScore.isAtLeast(2))
        ) {
            liverScore -= 10;
        }
        // 종합 점수 계산
        /*
        int totalScore = obesityScore
                + bpScore.getScore()
                + fastingGlucoseScore.getScore()
                + kidneysScore
                + liverScore
                + hemoglobinScore.getScore();

         */
        log.info("[HealthScore] BMI score={}, step={}",
                bmiScore.getScore(), bmiScore.getStep());

        log.info("[HealthScore] Waist score={}, step={}",
                waistScore.getScore(), waistScore.getStep());

        log.info("[HealthScore] BloodPressure score={}, step={}",
                bpScore.getScore(), bpScore.getStep());

        log.info("[HealthScore] FastingGlucose score={}, step={}",
                fastingGlucoseScore.getScore(), fastingGlucoseScore.getStep());

        log.info("[HealthScore] Hemoglobin score={}, step={}",
                hemoglobinScore.getScore(), hemoglobinScore.getStep());

        log.info("[HealthScore] Creatinine score={}, step={}",
                serumCreatinineScore.getScore(), serumCreatinineScore.getStep());

        log.info("[HealthScore] eGFR score={}, step={}",
                egfrScore.getScore(), egfrScore.getStep());

        log.info("[HealthScore] AST score={}, step={}",
                astScore.getScore(), astScore.getStep());

        log.info("[HealthScore] ALT score={}, step={}",
                altScore.getScore(), altScore.getStep());

        log.info("[HealthScore] GGT score={}, step={}",
                gammaGtpScore.getScore(), gammaGtpScore.getStep());

        log.info(" score={}, score={}, score={}, score={}, score={}, score={}",obesityScore,bpScore.getScore(),fastingGlucoseScore.getScore(),
                kidneysScore,liverScore,hemoglobinScore.getScore());
        double weightedScore =
                obesityScore * 0.20
                        + bpScore.getScore() * 0.20
                        + fastingGlucoseScore.getScore() * 0.20
                        + kidneysScore * 0.15
                        + liverScore * 0.15
                        + hemoglobinScore.getScore() * 0.10;

        // 소수점 반올림하여 정수로 표기
        int totalScore = (int) Math.round(weightedScore);
        return HealthScore.from((long) totalScore);

    }
}
