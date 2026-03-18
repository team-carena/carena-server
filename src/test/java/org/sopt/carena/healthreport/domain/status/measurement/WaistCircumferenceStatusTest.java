package org.sopt.carena.healthreport.domain.status.measurement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sopt.carena.member.domain.Gender;

class WaistCircumferenceStatusTest {

	@ParameterizedTest
	@CsvSource({
			"89.9, MALE, NORMAL",
			"90.0, MALE, ABDOMINAL_OBESITY",
			"84.9, FEMALE, NORMAL",
			"85.0, FEMALE, ABDOMINAL_OBESITY"
	})
	@DisplayName("성별과 허리둘레 수치에 따라 상태가 올바르게 판별된다.")
	void of(Double value, Gender gender, WaistCircumferenceStatus expected) {
		// when
		WaistCircumferenceStatus status = WaistCircumferenceStatus.of(value, gender);

		// then
		assertThat(status).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"MALE, NONE",
			"FEMALE, NONE"
	})
	@DisplayName("허리둘레 수치가 null이면 NONE 상태를 반환한다.")
	void ofNull(Gender gender, WaistCircumferenceStatus expected) {
		// when
		WaistCircumferenceStatus status = WaistCircumferenceStatus.of(null, gender);

		// then
		assertThat(status).isEqualTo(expected);
	}
}
