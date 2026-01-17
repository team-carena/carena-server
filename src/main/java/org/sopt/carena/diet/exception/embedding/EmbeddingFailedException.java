package org.sopt.carena.diet.exception.embedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class EmbeddingFailedException extends BaseException {

    public EmbeddingFailedException() {
        super(DietErrorCode.EMBEDDING_FAILED);
    }
}