package org.sopt.carena.diet.exception.enbedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class EnbeddingFailedException extends BaseException {

    public EnbeddingFailedException() {
        super(DietErrorCode.EMBEDDING_FAILED);
    }
}