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

@Tag(name = "건강검진 기관", description = "건강 검진 기관 조회 관련 API")
public interface InstitutionApiDocs {
	@Operation(summary = "시/도 주소 코드 조회", description = "검진 기관 검색에 필요한 시/도 단위의 주소 코드 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<SidoCodeView>> getSidoCode();

	@Operation(summary = "시/군/구 주소 코드 조회", description = "시/도 단위 주소 코드에 포함되는 시/군/구 단위의 주소 코드 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<SigunguCodeView>> getSigunguCode(int sidoCode);
}
