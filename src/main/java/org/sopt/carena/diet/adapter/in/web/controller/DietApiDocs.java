package org.sopt.carena.diet.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.carena.diet.adapter.in.web.dto.request.AdminDietRequest;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "식단 관리",description = "건강검진 결과에 따른 식단 추천을 위한 API")
public interface DietApiDocs {
    @Operation(summary = "식단 정보 생성 " ,description = "[ADMIN] 식단 정보 생성을 위한 어드민 기능입니다.")
    ResponseEntity<SuccessResponse<Void>> createDiet(Long memberId, AdminDietRequest request);
}

