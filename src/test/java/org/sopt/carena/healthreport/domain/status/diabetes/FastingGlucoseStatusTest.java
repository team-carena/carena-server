package org.sopt.carena.healthreport.domain.status.diabetes;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FastingGlucoseStatusTest {

	@ParameterizedTest
	@CsvSource({
			"0.0, NORMAL",
			"99.9, NORMAL",
			"100.0, IMPAIRED_FASTING_GLUCOSE",
			"125.0, IMPAIRED_FASTING_GLUCOSE",
			"125.1, DIABETES_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("공복 혈당 수치에 따라 상태가 올바르게 판별된다.")
	void from(Double value, FastingGlucoseStatus expected) {
		// when
		FastingGlucoseStatus status = FastingGlucoseStatus.from(value);

		// then
		assertThat(status).isEqualTo(expected);
	}
}
