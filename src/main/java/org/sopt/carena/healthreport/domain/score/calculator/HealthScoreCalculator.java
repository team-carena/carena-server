package org.sopt.carena.healthreport.domain.score.calculator;

import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.score.HealthScore;
import org.sopt.carena.healthreport.domain.score.ScoreItem;
import org.sopt.carena.healthreport.domain.score.metric.HealthMetric;
import org.sopt.carena.member.domain.Gender;
import org.springframework.stereotype.Component;

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

        // ScoreItem 배열 생성
        //혈압
        ScoreItem[] bpItems = bloodPressureScorePolicy.calculate(
                healthReport.getBloodPressure() != null ? healthReport.getBloodPressure().systolicBp() : null,
                healthReport.getBloodPressure() != null ? healthReport.getBloodPressure().diastolicBp() : null
        );

        ScoreItem[] allItems = {
                    (healthReport.getBmi() != null && healthReport.getBmi().value() != null) ?
                            HealthMetric.BMI.calculateResult(healthReport.getBmi().value(), gender) : null,
                    (healthReport.getWaistCircumference() != null && healthReport.getWaistCircumference().value() != null) ?
                            HealthMetric.WAIST_CIRCUMFERENCE.calculateResult(healthReport.getWaistCircumference().value(), gender) : null,
                    (healthReport.getSerumCreatinine() != null && healthReport.getSerumCreatinine().value() != null) ?
                            HealthMetric.SERUM_CREATININE.calculateResult(healthReport.getSerumCreatinine().value(), gender) : null,
                    (healthReport.getEgfr() != null && healthReport.getEgfr().value() != null) ?
                            HealthMetric.EGFR.calculateResult(healthReport.getEgfr().value(), gender) : null,
                    (healthReport.getAst() != null && healthReport.getAst().value() != null) ?
                            HealthMetric.AST.calculateResult(healthReport.getAst().value(), gender) : null,
                    (healthReport.getAlt() != null && healthReport.getAlt().value() != null) ?
                            HealthMetric.ALT.calculateResult(healthReport.getAlt().value(), gender) : null,
                    (healthReport.getGammaGtp() != null && healthReport.getGammaGtp().value() != null) ?
                            HealthMetric.GAMMA_GTP.calculateResult(healthReport.getGammaGtp().value(), gender) : null,
                    bpItems[0],  // 수축기
                    bpItems[1], // 이완기
                    (healthReport.getHemoglobin() != null && healthReport.getHemoglobin().value() != null) ?
                            HealthMetric.HEMOGLOBIN.calculateResult(healthReport.getHemoglobin().value(), gender) : null,
                    (healthReport.getFastingGlucose() != null && healthReport.getFastingGlucose().value() != null) ?
                            HealthMetric.FASTING_GLUCOSE.calculateResult(healthReport.getFastingGlucose().value(), gender) : null
            };


            // 모든 ScoreItem + 가중치 합산
            double weightedScoreSum = 0;
            double totalWeight = 0;

            String[] itemNames = {
                    "BMI", "WaistCircumference",
                    "Creatinine", "eGFR",
                    "AST", "ALT", "GGT",
                    "수축기BloodPressure","이완기BloodPressure",
                    "Hemoglobin", "FastingGlucose"
            };

            for (int i = 0; i < allItems.length; i++) {
                ScoreItem item = allItems[i];
                if (item != null) {
                    double weighted = item.importanceScore();
                    double weight = item.getImportance();
                    weightedScoreSum += weighted;
                    totalWeight += weight;

                    log.info("[HealthScore] {} - score={}, weight={}, weightedScore={}",
                            itemNames[i], item.getScore(), weight, weighted);
                } else {
                    log.info("[HealthScore] {} - 유효한 값 없음, 계산 제외", itemNames[i]);
                }
            }

            int finalScore = totalWeight > 0 ? (int) Math.round(weightedScoreSum / totalWeight) : 0;
            log.info("[HealthScore] weightedScoreSum={}, totalWeight={}, 최종 건강점수={}",
                    weightedScoreSum, totalWeight, finalScore);

            return HealthScore.from((long) finalScore);
        }
    }


