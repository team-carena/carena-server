package org.sopt.carena.diet.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.ErrorResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DietErrorCode implements ErrorResultCode {

    INVALID_SECTION_NAME(HttpStatus.BAD_REQUEST,"섹션 이름이 존재하지 않습니다."),
    SECTION_NAME_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"섹션 이름이 비어있습니다."),
    EMBEDDING_TEXT_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"임베딩 텍스트가 비어있습니다."),
    EMBEDDING_RESULT_NULL(HttpStatus.INTERNAL_SERVER_ERROR,"임베딩 결과가 비어있습니다."),
    CREATED_EMBEDDING_TEXT_FAILED(HttpStatus.BAD_GATEWAY,"임베딩 텍스트로 변환을 실패하였습니다."),
    EMBEDDING_FAILED(HttpStatus.BAD_GATEWAY,"임베딩을 실패하였습니다."),
    DIET_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 식단이 존재하지 않습니다."),
    DIET_ALREADY_EXIST(HttpStatus.CONFLICT,"해당 식단은 이미 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
