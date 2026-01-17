package org.sopt.carena.healthreport.application.dto.view;

public record ExtractedTextView(
		Double height,
		Double weight,
		Double waistCircumference,
		Double bmi,
		Integer systolicBp,
		Integer diastolicBp,
		Double hemoglobin,
		Double fastingGlucose,
		Double totalCholesterol,
		Double hdl,
		Double ldl,
		Double triglyceride,
		Double serumCreatinine,
		Double egfr,
		Double ast,
		Double alt,
		Double gammaGtp
) {
	public static ExtractedTextView of(
			final Double height,
			final Double weight,
			final Double waistCircumference,
			final Double bmi,
			final Integer systolicBp,
			final Integer diastolicBp,
			final Double hemoglobin,
			final Double fastingGlucose,
			final Double totalCholesterol,
			final Double hdl,
			final Double ldl,
			final Double triglyceride,
			final Double serumCreatinine,
			final Double egfr,
			final Double ast,
			final Double alt,
			final Double gammaGtp
	) {
		return new ExtractedTextView(height,
				weight,
				waistCircumference,
				bmi,
				systolicBp,
				diastolicBp,
				hemoglobin,
				fastingGlucose,
				totalCholesterol,
				hdl,
				ldl,
				triglyceride,
				serumCreatinine,
				egfr,
				ast,
				alt,
				gammaGtp
		);
	}
}