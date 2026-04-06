package org.sopt.carena.healthreport.domain.status.kidney;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KidneyStatusTest {

	@ParameterizedTest
	@CsvSource({
			"60.0, EGFR_NORMAL",
			"59.9, EGFR_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("eGFR 수치에 따라 상태가 올바르게 판별된다.")
	void egfrStatus(Double value, EgfrStatus expected) {
		assertThat(EgfrStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"1.5, SERUM_CREATININE_NORMAL",
			"1.6, SERUM_CREATININE_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("혈청 크레아티닌 수치에 따라 상태가 올바르게 판별된다.")
	void serumCreatinineStatus(Double value, SerumCreatinineStatus expected) {
		assertThat(SerumCreatinineStatus.from(value)).isEqualTo(expected);
	}
}
