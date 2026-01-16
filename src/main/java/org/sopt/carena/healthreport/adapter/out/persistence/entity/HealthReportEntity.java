package org.sopt.carena.healthreport.adapter.out.persistence.entity;

import java.time.LocalDate;

import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.domain.Gender;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "health_report", indexes = {
		@Index(name = "idx_health_report_member_id", columnList = "member_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthReportEntity {
	@Id
	@Tsid
	private Long id;

	@Column(name = "health_check_date", nullable = false)
	private LocalDate healthCheckDate;

	@Column(name = "institution_name", nullable = false)
	private String institutionName;

	@Column(name = "height")
	private Double height;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "waist_circumference")
	private Double waistCircumference;

	@Column(name = "bmi")
	private Double bmi;

	@Column(name = "systolic_bp")
	private Integer systolicBp;

	@Column(name = "diastolic_bp")
	private Integer diastolicBp;

	@Column(name = "hemoglobin")
	private Double hemoglobin;

	@Column(name = "fasting_glucose")
	private Double fastingGlucose;

	@Column(name = "total_cholesterol")
	private Double totalCholesterol;

	@Column(name = "hdl")
	private Double hdl;

	@Column(name = "ldl")
	private Double ldl;

	@Column(name = "triglyceride")
	private Double triglyceride;

	@Column(name = "serum_creatinine")
	private Double serumCreatinine;

	@Column(name = "egfr")
	private Double egfr;

	@Column(name = "ast")
	private Double ast;

	@Column(name = "alt")
	private Double alt;

	@Column(name = "gamma_gtp")
	private Double gammaGtp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity memberEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;

	@Builder
	private HealthReportEntity(
			final LocalDate healthCheckDate,
			final String institutionName,
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
			final Double gammaGtp,
			final MemberEntity memberEntity,
			final Gender gender
	) {
		this.healthCheckDate = healthCheckDate;
		this.institutionName = institutionName;
		this.height = height;
		this.weight = weight;
		this.waistCircumference = waistCircumference;
		this.bmi = bmi;
		this.systolicBp = systolicBp;
		this.diastolicBp = diastolicBp;
		this.hemoglobin = hemoglobin;
		this.fastingGlucose = fastingGlucose;
		this.totalCholesterol = totalCholesterol;
		this.hdl = hdl;
		this.ldl = ldl;
		this.triglyceride = triglyceride;
		this.serumCreatinine = serumCreatinine;
		this.egfr = egfr;
		this.ast = ast;
		this.alt = alt;
		this.gammaGtp = gammaGtp;
		this.memberEntity = memberEntity;
		this.gender = gender;
	}
}
