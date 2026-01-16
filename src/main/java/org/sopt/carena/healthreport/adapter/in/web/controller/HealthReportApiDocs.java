package org.sopt.carena.healthreport.adapter.in.web.controller;

import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthreport.adapter.in.web.request.CreateHealthReportRequest;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "건강 검진 결과", description = "건강 검진 결과 관련 API")
public interface HealthReportApiDocs {
	@Operation(summary = "건강 검진 결과 OCR", description = "검진 결과 이미지를 텍스트로 변환합니다.")
	ResponseEntity<SuccessResponse<ExtractedTextView>> extractText(MultipartFile file);

	@Operation(summary = "건강 검진 결과 저장", description = "건강 검진 결과를 저장합니다.")
	ResponseEntity<SuccessResponse<Void>> createHealthReport(long memberId, @Valid CreateHealthReportRequest request);
}
