package org.sopt.carena.healthreport.domain.status;

public interface HealthStatusCarrier {
	public String getDescription();

	public RiskLevel getRiskLevel();
}
