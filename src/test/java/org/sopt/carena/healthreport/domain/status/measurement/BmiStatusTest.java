package org.sopt.carena.healthreport.domain.status.measurement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BmiStatusTest {

	@ParameterizedTest(name = "BMI {0} -> {1}")
	@CsvSource({
			"0.0, UNDERWEIGHT",
			"18.4, UNDERWEIGHT",
			"18.5, NORMAL",
			"24.9, NORMAL",
			"25.0, OVERWEIGHT",
			"29.9, OVERWEIGHT",
			"30.0, OBESE"
	})
	@DisplayName("BMI 수치에 따라 상태가 올바르게 판별된다.")
	void from(Double value, BmiStatus expected) {
		// when
		BmiStatus status = BmiStatus.from(value);

		// then
		assertThat(status).isEqualTo(expected);
	}

	@Test
	@DisplayName("BMI 수치가 null이면 NONE 상태를 반환한다.")
	void fromNull() {
		// when
		BmiStatus status = BmiStatus.from(null);

		// then
		assertThat(status).isEqualTo(BmiStatus.NONE);
	}
}
