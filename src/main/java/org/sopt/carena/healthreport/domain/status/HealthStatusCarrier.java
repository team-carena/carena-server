package org.sopt.carena.healthreport.domain.status;

public interface HealthStatusCarrier {
	String getDescription();

	RiskLevel getRiskLevel();
}
