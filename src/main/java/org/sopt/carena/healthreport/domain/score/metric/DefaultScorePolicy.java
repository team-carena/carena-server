package org.sopt.carena.healthreport.domain.score.metric;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.healthreport.domain.score.ScoreItem;
import org.sopt.carena.healthreport.domain.score.rule.DeviationType;
import org.sopt.carena.member.domain.Gender;

import java.util.function.Function;

//기존 공식용 기본 정책
@RequiredArgsConstructor
public class DefaultScorePolicy implements ScorePolicy {

    private final Function<Gender, Double> minProvider;
    private final Function<Gender, Double> maxProvider;
    private final Function<Gender, Double> unitProvider;
    private final DeviationType deviationType;

    private final boolean lowerExclusive; // 하한 초과(true)/이상(false)
    private final boolean upperExclusive; // 상한 미만(true)/이하(false)


    @Override
    public ScoreItem calculate(Double value, Gender gender, double importance) {

        if (value == null) return new ScoreItem(0, 100, importance);

        double min = minProvider.apply(gender);
        double max = maxProvider.apply(gender);
        double unit = unitProvider.apply(gender);

        double deviation = 0;

        // 상방 이탈
        if (deviationType != DeviationType.LOWER_ONLY) {
            if (upperExclusive) { // 상한 미만
                if (value >= max) deviation = value - max + 1e-9;
            } else { // 상한 이하
                if (value > max) deviation = value - max;
            }
        }

        // 하방 이탈
        if (deviationType != DeviationType.UPPER_ONLY) {
            if (lowerExclusive) { // 하한 초과
                if (value <= min) deviation = min - value + 1e-9;
            } else { // 하한 이상
                if (value < min) deviation = min - value;
            }
        }

        // 정상 범위면 step 0
        if (deviation <= 0) return new ScoreItem(0, 100, importance);


        double stepsDouble = deviation / unit;
        int rawStep;

        // deviation/unit이 정수면 그대로, 아니면 올림
        if (Math.floor(stepsDouble) == stepsDouble) {
            rawStep = (int) stepsDouble;
        } else {
            rawStep = (int) Math.ceil(stepsDouble);
        }
        int step = Math.min(rawStep, 3);

        // 점수 계산
        int score = switch (step) {
            case 0 -> 100;
            case 1 -> 80;
            case 2 -> 60;
            default -> 40;
        };
        return new ScoreItem(step, score, importance);
    }
}