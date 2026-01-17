package org.sopt.carena.diet.exception.diet;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class DietNotFoundException extends BaseException {
    public DietNotFoundException() {
        super(DietErrorCode.DIET_NOT_FOUND);
    }
}
