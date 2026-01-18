package org.sopt.carena.diet.exception.diet;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class RecommendationNotFoundException extends BaseException {
    public RecommendationNotFoundException() {
        super(DietErrorCode.DIET_RECOMMEND_NOT_FOUND);
    }
}
