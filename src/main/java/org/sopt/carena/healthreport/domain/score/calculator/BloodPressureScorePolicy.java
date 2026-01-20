package org.sopt.carena.healthreport.domain.score.calculator;

import org.sopt.carena.healthreport.domain.score.ScoreItem;

public class BloodPressureScorePolicy {

    private static final double SYS_MAX = 120;
    private static final double DIA_MAX = 80;
    private static final double SYS_LOW = 90;
    private static final double DIA_LOW = 60;
    private static final double UNIT = 10.0;

    // 가중치
    public static final double SYS_WEIGHT = 0.12;
    public static final double DIA_WEIGHT = 0.08;

    public ScoreItem[] calculate(Integer systolic, Integer diastolic) {
        ScoreItem sysItem = null;
        ScoreItem diaItem = null;

        // 수축기
        if (systolic != null) {
            int systolicStep = calculateStep(systolic, SYS_LOW, SYS_MAX);
            int systolicScore = scoreFromStep(systolicStep);
            sysItem = new ScoreItem(systolicStep, systolicScore, SYS_WEIGHT);
        }

        // 이완기
        if (diastolic != null) {
            int diastolicStep = calculateStep(diastolic, DIA_LOW, DIA_MAX);
            int diastolicScore = scoreFromStep(diastolicStep);
            diaItem = new ScoreItem(diastolicStep, diastolicScore, DIA_WEIGHT);
        }
        return new ScoreItem[]{sysItem, diaItem};
    }

    private int calculateStep(int value, double min, double max) {
        if (value < min) return 1; // 하방 이탈 → 1단계만 반영
        if (value >= max) {
            double raw = (value + 1e-9 - max) / UNIT;
            int step;

            if (Math.floor(raw) == raw) {
                step = (int) raw;
            } else {
                step = (int) Math.ceil(raw);
            }
            return step;
        }
        return 0;
    }
    private int scoreFromStep(int step) {
        return switch (step) {
            case 0 -> 100;
            case 1 -> 80;
            case 2 -> 60;
            default -> 40;
        };
    }
}