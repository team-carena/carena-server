package org.sopt.carena.healthreport.application.dto.command;

import java.time.LocalDate;

import org.sopt.carena.healthreport.adapter.in.web.request.CreateHealthReportRequest;

public record CreateHealthReportCommand(
		long memberId,
		LocalDate healthCheckDate,
		String institutionName,
		Double height,
		Double weight,
		Double waistCircumference,
		Double bmi,
		Integer systolicBloodPressure,
		Integer diastolicBloodPressure,
		Double hemoglobin,
		Double fastingGlucose,
		Double totalCholesterol,
		Double hdl,
		Double ldl,
		Double triglycerides,
		Double serumCreatinine,
		Double egfr,
		Double ast,
		Double alt,
		Double gammaGtp
) {
	public static CreateHealthReportCommand of(final long memberId, final CreateHealthReportRequest request) {
		return new CreateHealthReportCommand(
				memberId,
				request.healthCheckDate(),
				request.institutionName(),
				request.height(),
				request.weight(),
				request.waistCircumference(),
				request.bmi(),
				request.systolicBp(),
				request.diastolicBp(),
				request.hemoglobin(),
				request.fastingGlucose(),
				request.totalCholesterol(),
				request.hdl(),
				request.ldl(),
				request.triglycerides(),
				request.serumCreatinine(),
				request.egfr(),
				request.ast(),
				request.alt(),
				request.gammaGtp()
		);
	}
}
