package org.sopt.carena.diet.exception.embedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class SectionNameNullException extends BaseException {
    public SectionNameNullException() {
        super(DietErrorCode.SECTION_NAME_NULL);
    }
}
