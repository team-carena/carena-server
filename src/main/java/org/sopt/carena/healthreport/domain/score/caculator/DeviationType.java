package org.sopt.carena.healthreport.domain.score.caculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 건강 지표의 이탈 방향 타입
 */
@Getter
@RequiredArgsConstructor
public enum DeviationType {
    /**
     * 상방이탈만 (값이 기준보다 높으면 안좋음)
     * 예: 허리둘레, 공복혈당, 크레아티닌, AST, ALT, 감마GTP
     */
    UPPER_ONLY,

    /**
     * 하방이탈만 (값이 기준보다 낮으면 안좋음)
     * 예: 혈색소, 신사구체여과율
     */
    LOWER_ONLY,

    /**
     * 양방향 이탈 (기준보다 높거나 낮으면 안좋음)
     * 예: BMI
     */
    BIDIRECTIONAL
}