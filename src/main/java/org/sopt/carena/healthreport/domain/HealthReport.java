package org.sopt.carena.healthreport.domain;

import java.time.LocalDate;
import java.util.List;

import org.sopt.carena.healthreport.application.dto.commend.CreateHealthReportCommand;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.healthreport.domain.value.liver.Alt;
import org.sopt.carena.healthreport.domain.value.liver.Ast;
import org.sopt.carena.healthreport.domain.value.measurement.BloodPressure;
import org.sopt.carena.healthreport.domain.value.measurement.Bmi;
import org.sopt.carena.healthreport.domain.value.kidney.Egfr;
import org.sopt.carena.healthreport.domain.value.diabetes.FastingGlucose;
import org.sopt.carena.healthreport.domain.value.liver.GammaGtp;
import org.sopt.carena.healthreport.domain.value.dyslipidemia.Hdl;
import org.sopt.carena.healthreport.domain.value.measurement.Height;
import org.sopt.carena.healthreport.domain.value.anemia.Hemoglobin;
import org.sopt.carena.healthreport.domain.value.dyslipidemia.Ldl;
import org.sopt.carena.healthreport.domain.value.kidney.SerumCreatinine;
import org.sopt.carena.healthreport.domain.value.dyslipidemia.TotalCholesterol;
import org.sopt.carena.healthreport.domain.value.dyslipidemia.Triglyceride;
import org.sopt.carena.healthreport.domain.value.measurement.WaistCircumference;
import org.sopt.carena.healthreport.domain.value.measurement.Weight;
import org.sopt.carena.member.domain.Gender;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HealthReport {
	private long id;
	private long memberId;
	private Gender gender;
	private LocalDate healthCheckDate;
	private String institutionName;

	// measurement
	private Height height;
	private Weight weight;
	private WaistCircumference waistCircumference;
	private Bmi bmi;
	private BloodPressure bloodPressure;

	// anemia
	private Hemoglobin hemoglobin;

	// diabetes
	private FastingGlucose fastingGlucose;

	// dyslipidemia
	private TotalCholesterol totalCholesterol;
	private Hdl hdl;
	private Ldl ldl;
	private Triglyceride triglyceride;

	// kidney
	private SerumCreatinine serumCreatinine;
	private Egfr egfr;

	// liver
	private Ast ast;
	private Alt alt;
	private GammaGtp gammaGtp;

	@Builder
	private HealthReport(
			final long id,
			final long memberId,
			final Gender gender,
			final LocalDate healthCheckDate,
			final String institutionName,
			final Double height,
			final Double weight,
			final Double waistCircumference,
			final Double bmi,
			final Integer systolicBloodPressure,
			final Integer diastolicBloodPressure,
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
			){
		this.id = id;
		this.memberId = memberId;
		this.gender = gender;
		this.healthCheckDate = healthCheckDate;
		this.institutionName = institutionName;
		this.height = Height.from(height);
		this.weight = Weight.from(weight);
		this.waistCircumference = WaistCircumference.of(waistCircumference, gender);
		this.bmi = Bmi.from(bmi);
		this.bloodPressure = BloodPressure.of(systolicBloodPressure, diastolicBloodPressure);
		this.hemoglobin = Hemoglobin.of(hemoglobin, gender);
		this.fastingGlucose = FastingGlucose.from(fastingGlucose);
		this.totalCholesterol = TotalCholesterol.from(totalCholesterol);
		this.hdl = Hdl.from(hdl);
		this.ldl = Ldl.from(ldl);
		this.triglyceride = Triglyceride.from(triglyceride);
		this.serumCreatinine = SerumCreatinine.from(serumCreatinine);
		this.egfr = Egfr.from(egfr);
		this.ast = Ast.from(ast);
		this.alt = Alt.from(alt);
		this.gammaGtp = GammaGtp.of(gammaGtp, gender);
	}

	public static HealthReport create(final CreateHealthReportCommand commend, final Gender gender) {
		return HealthReport.builder()
				.memberId(commend.memberId())
				.gender(gender)
				.healthCheckDate(commend.healthCheckDate())
				.institutionName(commend.institutionName())
				.height(commend.height())
				.weight(commend.weight())
				.waistCircumference(commend.waistCircumference())
				.bmi(commend.bmi())
				.systolicBloodPressure(commend.systolicBloodPressure())
				.diastolicBloodPressure(commend.diastolicBloodPressure())
				.hemoglobin(commend.hemoglobin())
				.fastingGlucose(commend.fastingGlucose())
				.totalCholesterol(commend.totalCholesterol())
				.hdl(commend.hdl())
				.ldl(commend.ldl())
				.triglyceride(commend.triglycerides())
				.serumCreatinine(commend.serumCreatinine())
				.egfr(commend.egfr())
				.ast(commend.ast())
				.alt(commend.alt())
				.gammaGtp(commend.gammaGtp())
				.build();
	}

	public List<HealthStatusCarrier> getStatusCarriers() {
		return List.of(
				waistCircumference.status(),
				bmi.status(),
				bloodPressure.status(),
				hemoglobin.status(),
				fastingGlucose.status(),
				totalCholesterol.status(),
				hdl.status(),
				ldl.status(),
				triglyceride.status(),
				serumCreatinine.status(),
				egfr.status(),
				ast.status(),
				alt.status(),
				gammaGtp.status()
		);
	}
}
