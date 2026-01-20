package org.sopt.carena.recommend.exception;

import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.recommend.exception.code.ErrorCode;

public class RecommendedMealNotFoundException extends BaseException {
	public RecommendedMealNotFoundException() {
		super(ErrorCode.RECOMMENDED_MEAL_NOT_FOUND);
	}
}
