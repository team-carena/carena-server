package org.sopt.carena.healthreport.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.carena.healthreport.application.dto.command.CreateHealthReportCommand;
import org.sopt.carena.healthreport.application.dto.command.UpdateHealthReportCommand;
import org.sopt.carena.healthreport.domain.status.HealthStatusCarrier;
import org.sopt.carena.member.domain.Gender;

class HealthReportTest {

	@Test
	@DisplayName("CreateHealthReportCommand를 통해 HealthReport를 생성할 수 있다.")
	void createHealthReport() {
		// given
		CreateHealthReportCommand command = new CreateHealthReportCommand(
				1L, LocalDate.of(2024, 1, 1), "Test Hospital",
				170.0, 70.0, 85.0, 24.2, 120, 80,
				15.0, 95.0, 190.0, 50.0, 120.0, 140.0,
				1.0, 90.0, 25.0, 20.0, 30.0
		);

		// when
		HealthReport healthReport = HealthReport.create(command, Gender.MALE);

		// then
		assertThat(healthReport.getMemberId()).isEqualTo(1L);
		assertThat(healthReport.getGender()).isEqualTo(Gender.MALE);
		assertThat(healthReport.getInstitutionName()).isEqualTo("Test Hospital");
		assertThat(healthReport.getBmi().value()).isEqualTo(24.2);
		assertThat(healthReport.getBloodPressure().systolicBp()).isEqualTo(120);
	}

	@Test
	@DisplayName("UpdateHealthReportCommand를 통해 HealthReport를 수정할 수 있다.")
	void updateHealthReport() {
		// given
		CreateHealthReportCommand createCommand = new CreateHealthReportCommand(
				1L, LocalDate.of(2024, 1, 1), "Test Hospital",
				170.0, 70.0, 85.0, 24.2, 120, 80,
				15.0, 95.0, 190.0, 50.0, 120.0, 140.0,
				1.0, 90.0, 25.0, 20.0, 30.0
		);
		HealthReport healthReport = HealthReport.create(createCommand, Gender.MALE);

		UpdateHealthReportCommand updateCommand = new UpdateHealthReportCommand(
				1L, 1L, LocalDate.of(2024, 2, 1), "New Hospital",
				171.0, 71.0, 86.0, 24.5, 130, 85,
				16.0, 100.0, 200.0, 55.0, 130.0, 150.0,
				1.1, 85.0, 30.0, 25.0, 35.0
		);

		// when
		healthReport.update(updateCommand);

		// then
		assertThat(healthReport.getHealthCheckDate()).isEqualTo(LocalDate.of(2024, 2, 1));
		assertThat(healthReport.getInstitutionName()).isEqualTo("New Hospital");
		assertThat(healthReport.getHeight().value()).isEqualTo(171.0);
		assertThat(healthReport.getBloodPressure().systolicBp()).isEqualTo(130);
	}

	@Test
	@DisplayName("HealthReport의 모든 상태 캐리어를 가져올 수 있다.")
	void getStatusCarriers() {
		// given
		CreateHealthReportCommand command = new CreateHealthReportCommand(
				1L, LocalDate.of(2024, 1, 1), "Test Hospital",
				170.0, 70.0, 85.0, 24.2, 120, 80,
				15.0, 95.0, 190.0, 50.0, 120.0, 140.0,
				1.0, 90.0, 25.0, 20.0, 30.0
		);
		HealthReport healthReport = HealthReport.create(command, Gender.MALE);

		// when
		List<HealthStatusCarrier> carriers = healthReport.getStatusCarriers();

		// then
		assertThat(carriers).hasSize(14);
	}
}
