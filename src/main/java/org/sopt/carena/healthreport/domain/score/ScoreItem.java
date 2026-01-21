package org.sopt.carena.healthreport.domain.score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScoreItem{
    private final int step;   // 0,1,2,3
    private final int score;  // 100,80,60,40
    private final double importance;//가중치

    public boolean isAtLeast(int targetStep) {
        return step >= targetStep;
    }
    public double importanceScore() {
        return score*importance;
    }
}
