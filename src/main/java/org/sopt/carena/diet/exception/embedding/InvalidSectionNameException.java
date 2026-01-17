package org.sopt.carena.diet.exception.embedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class InvalidSectionNameException extends BaseException {
    public InvalidSectionNameException() {
        super(DietErrorCode.INVALID_SECTION_NAME);
    }
}

