package org.sopt.carena.diet.exception.enbedding;

import org.sopt.carena.diet.exception.code.DietErrorCode;
import org.sopt.carena.global.exception.BaseException;

public class CreateEmbeddingTextFailedException extends BaseException {

    public CreateEmbeddingTextFailedException() {
        super(DietErrorCode.CREATED_EMBEDDING_TEXT_FAILED);
    }
}