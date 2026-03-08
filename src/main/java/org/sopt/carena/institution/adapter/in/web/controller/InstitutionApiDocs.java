package org.sopt.carena.institution.adapter.in.web.controller;

import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.institution.application.dto.view.InstitutionListView;
import org.sopt.carena.institution.application.dto.view.SidoCodeView;
import org.sopt.carena.institution.application.dto.view.SigunguCodeView;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Tag(name = "건강검진 기관", description = "건강 검진 기관 조회 관련 API")
public interface InstitutionApiDocs {
	@Operation(summary = "시/도 주소 코드 조회", description = "검진 기관 검색에 필요한 시/도 단위의 주소 코드 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<SidoCodeView>> getSidoCode();

	@Operation(summary = "시/군/구 주소 코드 조회", description = "시/도 단위 주소 코드에 포함되는 시/군/구 단위의 주소 코드 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<SigunguCodeView>> getSigunguCode(@NotNull int sidoCode);

	@Operation(summary = "건강검진 기관 조회", description = "검색 파라미터 조건에 해당하는 건강검진 기관 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<InstitutionListView>> getInstitutionInfo(@Min(1) int page, Integer sidoCode, Integer sigunguCode, @Min(0) @Max(6) int type, String name);
}
