package org.sopt.carena.healthreport.adapter.in.web.controller;

import java.time.LocalDate;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthreport.adapter.in.web.request.CreateHealthReportRequest;
import org.sopt.carena.healthreport.adapter.in.web.code.SuccessCode;
import org.sopt.carena.healthreport.application.dto.command.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.EntireHealthReportView;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.dto.view.HealthReportDateListView;
import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryView;
import org.sopt.carena.healthreport.application.port.in.CreateHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.in.ExtractTextFromImageUseCase;
import org.sopt.carena.healthreport.application.port.in.GetEntireHealthReportUseCase;
import org.sopt.carena.healthreport.application.port.in.GetReportDateListUseCase;
import org.sopt.carena.healthreport.application.port.in.HealthReportItemHistoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/health-report")
public class HealthReportController implements HealthReportApiDocs {
	private final ExtractTextFromImageUseCase extractTextFromImageUseCase;
	private final CreateHealthReportUseCase createHealthReportUseCase;
	private final GetReportDateListUseCase getReportDateListUseCase;
	private final GetEntireHealthReportUseCase getEntireHealthReportUseCase;
	private final HealthReportItemHistoryUseCase healthReportItemHistoryUseCase;

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

	@GetMapping(path = "/dates")
	public ResponseEntity<SuccessResponse<HealthReportDateListView>> getReportDateList(
			@AuthenticationPrincipal final long id,
			@RequestParam(name = "index", defaultValue = "1") @Min(1) final int index
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						getReportDateListUseCase.getReportDateList(id, index)));
	}

	@GetMapping(path = "/{healthReportId}")
	public ResponseEntity<SuccessResponse<EntireHealthReportView>> getEntireHealthReport(
			@AuthenticationPrincipal final long memberId,
			@PathVariable(name = "healthReportId") final String healthReportId
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						getEntireHealthReportUseCase.getEntireHealthReport(memberId, Long.parseLong(healthReportId))));
	}

	@GetMapping(path = "/measurement/height")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getHeightHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadHeightHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/measurement/weight")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getWeightHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadWeightHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/measurement/waist-circumference")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getWaistCircumferenceHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadWaistCircumferenceHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/measurement/bmi")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getBmiHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadBmiHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/blood-pressure/systolic")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getSystolicBpHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadSystolicBpHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/blood-pressure/diastolic")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getDiastolicBpHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadDiastolicBpHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/anemia/hemoglobin")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getHemoglobinHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadHemoglobinHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/diabetes/fasting-glucose")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getFastingGlucoseHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadFastingGlucoseHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/liver/ast")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getAstHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadAstHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/liver/alt")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getAltHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadAltHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/liver/gamma-gtp")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getGammaGtpHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadGammaGtpHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/kidney/serum-creatinine")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getSerumCreatinineHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadSerumCreatinineHistory(memberId, healthCheckDate)));
	}

	@GetMapping(path = "/kidney/egfr")
	public ResponseEntity<SuccessResponse<HealthReportHistoryView>> getEgfrHistory(
			@AuthenticationPrincipal final long memberId,
			@RequestParam(name = "healthCheckDate") final LocalDate healthCheckDate
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_REPORT_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_REPORT_FOUND,
						healthReportItemHistoryUseCase.loadEgfrHistory(memberId, healthCheckDate)));
	}
}
