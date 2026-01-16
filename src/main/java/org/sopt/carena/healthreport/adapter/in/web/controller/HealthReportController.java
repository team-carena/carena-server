package org.sopt.carena.healthreport.adapter.in.web.controller;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthreport.adapter.in.web.request.CreateHealthReportRequest;
import org.sopt.carena.healthreport.adapter.in.web.code.SuccessCode;
import org.sopt.carena.healthreport.application.dto.commend.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.dto.commend.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.in.ExtractTextFromImageUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/health-report")
public class HealthReportController implements HealthReportApiDocs {
	private final ExtractTextFromImageUseCase extractTextFromImageUseCase;
	private final CreateHealthReportUseCase createHealthReportUseCase;

	@PostMapping(path = "/ocr")
	public ResponseEntity<SuccessResponse<ExtractedTextView>> extractText(
			@RequestParam(name = "file") final MultipartFile file
	) {
		return ResponseEntity.status(SuccessCode.EXTRACT_TEXT_SUCCESS.getStatus())
				.body(ApiResponse.success(SuccessCode.EXTRACT_TEXT_SUCCESS,
						extractTextFromImageUseCase.extractTextFromImage(ExtractTextCommand.from(file))));
	}

	@PostMapping
	public ResponseEntity<SuccessResponse<Void>> createHealthReport(
			@AuthenticationPrincipal final long memberId,
			@Valid @RequestBody final CreateHealthReportRequest request
	) {
		createHealthReportUseCase.createHealthReport(CreateHealthReportCommand.of(memberId, request));

		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_CREATED.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_CREATED));
	}
}
