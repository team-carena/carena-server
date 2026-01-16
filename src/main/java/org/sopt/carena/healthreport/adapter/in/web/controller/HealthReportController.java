package org.sopt.carena.healthreport.adapter.in.web.controller;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthreport.adapter.in.web.code.SuccessCode;
import org.sopt.carena.healthreport.application.dto.commend.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.port.in.ExtractTextFromImageUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/health-report")
public class HealthReportController implements HealthReportApiDocs {
	private final ExtractTextFromImageUseCase extractTextFromImageUseCase;

	@PostMapping(path = "/ocr")
	public ResponseEntity<SuccessResponse<ExtractedTextView>> extractText(
			@RequestParam(name = "file") final MultipartFile file
	) {
		return ResponseEntity.status(SuccessCode.EXTRACT_TEXT_SUCCESS.getStatus())
				.body(ApiResponse.success(SuccessCode.EXTRACT_TEXT_SUCCESS,
						extractTextFromImageUseCase.extractTextFromImage(ExtractTextCommand.from(file))));
	}
}
