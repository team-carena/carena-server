package org.sopt.carena.healthreport.adapter.in.web.controller;

import java.time.LocalDate;

import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthreport.adapter.in.web.request.WriteHealthReportRequest;
import org.sopt.carena.healthreport.application.dto.view.EntireHealthReportView;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.dto.view.HealthReportDateListView;
import org.sopt.carena.healthreport.application.dto.view.HealthReportHistoryView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "건강 검진 결과", description = "건강 검진 결과 관련 API")
public interface HealthReportApiDocs {
	@Operation(summary = "건강 검진 결과 OCR", description = "검진 결과 이미지를 텍스트로 변환합니다.")
	ResponseEntity<SuccessResponse<ExtractedTextView>> extractText(MultipartFile file);

	@Operation(summary = "건강 검진 결과 저장", description = "건강 검진 결과를 저장합니다.")
	ResponseEntity<SuccessResponse<Void>> createHealthReport(long memberId, @Valid WriteHealthReportRequest request);

	@Operation(summary = "건강 검진 결과 수정", description = "건강 검진 결과를 수정합니다.")
	ResponseEntity<SuccessResponse<Void>> updateHealthReport(long memberId, String healthReportId, @Valid WriteHealthReportRequest request);

	@Operation(summary = "저장된 건강 검진 결과 데이터의 날짜 목록 조회", description = "건강 검진 결과 데이터의 날짜 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportDateListView>> getReportDateList(long id, @Min(1) int index);

	@Operation(summary = "건강 검진 결과 중 내용 전체 조회", description = "식별자에 해당하는 건강 검진 결과의 전체 내용을 조회합니다.")
	ResponseEntity<SuccessResponse<EntireHealthReportView>> getEntireHealthReport(long memberId, String healthReportId);

	@Operation(summary = "건강 검진 결과 중 신장(키) 항목 히스토리 조회", description = "신장(키) 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getHeightHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 결과 중 체중 항목 히스토리 조회", description = "체중 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getWeightHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 결과 중 허리 둘레 항목 히스토리 조회", description = "허리 둘레 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getWaistCircumferenceHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 체질량 지수 항목 히스토리 조회", description = "체질량 지수 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getBmiHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 수축기 혈압 항목 히스토리 조회", description = "수축기 혈압 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getSystolicBpHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 이완기 혈압 항목 히스토리 조회", description = "이완기 혈압 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getDiastolicBpHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 혈색소 항목 히스토리 조회", description = "혈색소 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getHemoglobinHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 공복 혈당 항목 히스토리 조회", description = "공복 혈당 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getFastingGlucoseHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 AST 항목 히스토리 조회", description = "AST 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getAstHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 ALT 항목 히스토리 조회", description = "ALT 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getAltHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 감마 지티피 항목 히스토리 조회", description = "감마 지티피 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getGammaGtpHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 혈청 크레아티닌 항목 히스토리 조회", description = "혈청 크레아티닌 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getSerumCreatinineHistory(long memberId, LocalDate healthCheckDate);

	@Operation(summary = "건강 검진 중 결과 중 신사구체여과율 항목 히스토리 조회", description = "신사구체여과율 항목에 대해 최근 5개의 기록을 조회합니다.")
	ResponseEntity<SuccessResponse<HealthReportHistoryView>> getEgfrHistory(long memberId, LocalDate healthCheckDate);
}
