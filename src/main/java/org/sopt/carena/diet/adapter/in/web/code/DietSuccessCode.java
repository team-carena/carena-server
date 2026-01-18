package org.sopt.carena.diet.adapter.in.web.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.global.api.code.SuccessResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DietSuccessCode implements SuccessResultCode {

    DIET_CREATED(HttpStatus.CREATED,"식단 정보가 생성되었습니다."),
    DIET_DETAIL(HttpStatus.OK,"식단이 상세조회되었습니다."),
    DIET_LIST(HttpStatus.OK,"식단 리스트가 조회되었습니다."),
    DIET_RECOMMEND(HttpStatus.OK,"식단 추천이 조회되었습니다.");

    private final HttpStatus status;
    private final String message;
}
