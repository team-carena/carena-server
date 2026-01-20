package org.sopt.carena.healthreport.domain.score;

import lombok.Getter;

@Getter
public class HealthScore {
    private final Long value;

    private HealthScore(Long value) {
        this.value = validate(value);
    }

    public static HealthScore from(Long value) {
        return new HealthScore(value);
    }

    private Long validate(Long value) {
        if (value < 0) {
            return 0L;
        }
        if (value > 100) {
            return 100L;
        }
        return value;
    }
}
