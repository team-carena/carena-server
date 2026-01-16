package org.sopt.carena.diet.exception.embedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class EmbeddingResultNullException extends BaseException {
    public EmbeddingResultNullException() {
        super(DietErrorCode.EMBEDDING_RESULT_NULL);
    }
}
