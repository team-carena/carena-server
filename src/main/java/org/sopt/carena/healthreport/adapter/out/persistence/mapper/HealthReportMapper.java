package org.sopt.carena.healthreport.adapter.out.persistence.mapper;

import org.sopt.carena.healthreport.adapter.out.persistence.entity.HealthReportEntity;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;

public class HealthReportMapper {
	public static HealthReport toDomain(final HealthReportEntity entity) {
		return HealthReport.builder()
				.id(entity.getId())
				.memberId(entity.getMemberEntity().getId())
				.gender(entity.getGender())
				.healthCheckDate(entity.getHealthCheckDate())
				.institutionName(entity.getInstitutionName())
				.height(entity.getHeight())
				.weight(entity.getWeight())
				.waistCircumference(entity.getWaistCircumference())
				.bmi(entity.getBmi())
				.systolicBloodPressure(entity.getSystolicBp())
				.diastolicBloodPressure(entity.getDiastolicBp())
				.hemoglobin(entity.getHemoglobin())
				.fastingGlucose(entity.getFastingGlucose())
				.totalCholesterol(entity.getTotalCholesterol())
				.hdl(entity.getHdl())
				.ldl(entity.getLdl())
				.triglyceride(entity.getTriglyceride())
				.serumCreatinine(entity.getSerumCreatinine())
				.egfr(entity.getEgfr())
				.ast(entity.getAst())
				.alt(entity.getAlt())
				.gammaGtp(entity.getGammaGtp())
				.build();

	}

	public static HealthReportEntity toEntity(final HealthReport domain) {
		return HealthReportEntity.builder()
				.gender(domain.getGender())
				.healthCheckDate(domain.getHealthCheckDate())
				.institutionName(domain.getInstitutionName())
				.height(domain.getHeight().value())
				.weight(domain.getWeight().value())
				.waistCircumference(domain.getWaistCircumference().value())
				.bmi(domain.getBmi().value())
				.systolicBp(domain.getBloodPressure().systolicBp())
				.diastolicBp(domain.getBloodPressure().diastolicBp())
				.hemoglobin(domain.getHemoglobin().value())
				.fastingGlucose(domain.getFastingGlucose().value())
				.totalCholesterol(domain.getTotalCholesterol().value())
				.hdl(domain.getHdl().value())
				.ldl(domain.getLdl().value())
				.triglyceride(domain.getTriglyceride().value())
				.serumCreatinine(domain.getSerumCreatinine().value())
				.egfr(domain.getEgfr().value())
				.ast(domain.getAst().value())
				.alt(domain.getAlt().value())
				.gammaGtp(domain.getGammaGtp().value())
				.build();
	}

	public static HealthReportEntity toEntity(final HealthReport domain, final MemberEntity memberEntity) {
		return HealthReportEntity.builder()
				.gender(domain.getGender())
				.healthCheckDate(domain.getHealthCheckDate())
				.institutionName(domain.getInstitutionName())
				.height(domain.getHeight().value())
				.weight(domain.getWeight().value())
				.waistCircumference(domain.getWaistCircumference().value())
				.bmi(domain.getBmi().value())
				.systolicBp(domain.getBloodPressure().systolicBp())
				.diastolicBp(domain.getBloodPressure().diastolicBp())
				.hemoglobin(domain.getHemoglobin().value())
				.fastingGlucose(domain.getFastingGlucose().value())
				.totalCholesterol(domain.getTotalCholesterol().value())
				.hdl(domain.getHdl().value())
				.ldl(domain.getLdl().value())
				.triglyceride(domain.getTriglyceride().value())
				.serumCreatinine(domain.getSerumCreatinine().value())
				.egfr(domain.getEgfr().value())
				.ast(domain.getAst().value())
				.alt(domain.getAlt().value())
				.gammaGtp(domain.getGammaGtp().value())
				.memberEntity(memberEntity)
				.build();
	}
}
