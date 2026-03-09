package org.sopt.carena.healthreport.adapter.in.web.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WriteHealthReportRequest(
		@NotNull
		LocalDate healthCheckDate,
		@NotBlank
		String institutionName,
		@Positive
		Double height,
		@Positive
		Double weight,
		@Positive
		Double waistCircumference,
		@Positive
		Double bmi,
		@Positive
		Integer systolicBp,
		@Positive
		Integer diastolicBp,
		@Positive
		Double hemoglobin,
		@Positive
		Double fastingGlucose,
		@Positive
		Double totalCholesterol,
		@Positive
		Double hdl,
		@Positive
		Double ldl,
		@Positive
		Double triglycerides,
		@Positive
		Double serumCreatinine,
		@Positive
		Double egfr,
		@Positive
		Double ast,
		@Positive
		Double alt,
		@Positive
		Double gammaGtp
) {
}
