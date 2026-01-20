package org.sopt.carena.healthreport.domain.score.caculator;

import org.sopt.carena.healthreport.domain.score.ScoreItem;

public class BloodPressureScorePolicy {

    private static final double SYS_MAX = 120;
    private static final double DIA_MAX = 80;
    private static final double SYS_LOW = 90;
    private static final double DIA_LOW = 60;
    private static final double UNIT = 10.0;

    public ScoreItem calculate(Integer systolic, Integer diastolic) {
        if (systolic == null || diastolic == null)
            return new ScoreItem(0, 100);

        int systolicStep = 0;
        int diastolicStep = 0;

        // 수축기 상방 이탈
        if (systolic > SYS_MAX) {
            systolicStep = (int) ((systolic - SYS_MAX) / UNIT);
        }

        // 이완기 상방 이탈
        if (diastolic > DIA_MAX) {
            diastolicStep = (int) ((diastolic - DIA_MAX) / UNIT);
        }

        //  하방 이탈
        if (systolic < SYS_LOW) {
            systolicStep = Math.max(systolicStep, 1);
        }
        if (diastolic < DIA_LOW) {
            diastolicStep = Math.max(diastolicStep, 1);
        }

        // 1단계까지만 반영
        systolicStep = Math.min(systolicStep, 3);
        diastolicStep = Math.min(diastolicStep, 3);


        int step = Math.max(systolicStep, diastolicStep);

        // =============================
        // 여기부터 점수 계산만 변경
        // =============================

        int systolicScore = switch (systolicStep) {
            case 0 -> 100;
            case 1 -> 80;
            case 2 -> 60;
            default -> 40;
        };

        int diastolicScore = switch (diastolicStep) {
            case 0 -> 100;
            case 1 -> 80;
            case 2 -> 60;
            default -> 40;
        };

        // 평균 점수
        int score = (systolicScore + diastolicScore) / 2;

        //  추가 감점 규칙
        if (systolicStep >= 2 && diastolicStep >= 2) {
            score -= 10;
        }

        if (systolicStep >= 3 || diastolicStep >= 3) {
            score -= 20;
        }

        score = Math.max(score, 0);

        return new ScoreItem(step, score);
    }
}