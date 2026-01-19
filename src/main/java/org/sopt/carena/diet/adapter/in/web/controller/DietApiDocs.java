package org.sopt.carena.diet.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.sopt.carena.diet.adapter.in.web.dto.request.CreateAdminDietRequest;
import org.sopt.carena.diet.adapter.in.web.dto.response.DietDetailResponse;
import org.sopt.carena.diet.adapter.in.web.dto.response.DietListResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "식단 관리",description = "건강검진 결과에 따른 식단 추천을 위한 API")
public interface DietApiDocs {
    @Operation(summary = "식단 정보 생성 " ,description = "[ADMIN] 식단 정보 생성을 위한 어드민 기능입니다.")
    ResponseEntity<SuccessResponse<Void>> createDiet(@Valid CreateAdminDietRequest request);

    @Operation(summary = "식단 상세 조회" , description = "해당 식단의 제목, 내용, 추천식품, 비추천식품을 반환해줍니다.")
    ResponseEntity<SuccessResponse<DietDetailResponse>> dietDetail(Long id);

    @Operation(summary = "추천 식단 리스트 조회" , description = "건강검진 결과를 바탕으로 추천 식단 리스트를 조회합니다.")
    ResponseEntity<SuccessResponse<DietListResponse>> getDietList(long memberId, @Min(1) int page) ;
}

