package org.sopt.carena.healthreport.domain.score.caculator.metric;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.healthreport.domain.score.ScoreResult;
import org.sopt.carena.healthreport.domain.score.caculator.DeviationType;
import org.sopt.carena.member.domain.Gender;

import java.util.function.Function;

//기존 공식용 기본 정책
@RequiredArgsConstructor
public class DefaultScorePolicy implements ScorePolicy {

    private final Function<Gender, Double> minProvider;
    private final Function<Gender, Double> maxProvider;
    private final Function<Gender, Double> unitProvider;
    private final DeviationType deviationType;

    @Override
    public ScoreResult calculate(Double value, Gender gender) {
        if (value == null) return new ScoreResult(0, 100);

        double min = minProvider.apply(gender);
        double max = maxProvider.apply(gender);
        double unit = unitProvider.apply(gender);

        double deviation = 0;

        if (value < min) deviation = min - value;
        else if (value > max) deviation = value - max;
        else return new ScoreResult(0, 100);

        // 방향 필터
        if (deviationType == DeviationType.UPPER_ONLY && value <= max)
            return new ScoreResult(0, 100);

        if (deviationType == DeviationType.LOWER_ONLY && value >= min)
            return new ScoreResult(0, 100);

        int step = (int) Math.floor(deviation / unit);

        int score = switch (step) {
            case 0 -> 100;
            case 1 -> 80;
            case 2 -> 60;
            default -> 40;
        };

        return new ScoreResult(step, score);
    }
}