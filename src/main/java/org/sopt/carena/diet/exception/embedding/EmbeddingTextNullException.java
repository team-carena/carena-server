package org.sopt.carena.diet.exception.embedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class EmbeddingTextNullException extends BaseException {

    public EmbeddingTextNullException() {
        super(DietErrorCode.EMBEDDING_TEXT_NULL);
    }
}
