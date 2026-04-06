package org.sopt.carena.healthreport.domain.status.anemia;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sopt.carena.member.domain.Gender;

class HemoglobinStatusTest {

	@ParameterizedTest
	@CsvSource({
			"16.6, MALE, POLYCYTHEMIA_SUSPICIOUS",
			"13.0, MALE, NORMAL",
			"12.1, MALE, ANEMIA_BORDERLINE",
			"12.0, MALE, ANEMIA_SUSPICIOUS",
			"15.0, FEMALE, POLYCYTHEMIA_SUSPICIOUS",
			"12.0, FEMALE, NORMAL",
			"10.1, FEMALE, ANEMIA_BORDERLINE",
			"10.0, FEMALE, ANEMIA_SUSPICIOUS",
			", MALE, NONE",
			", FEMALE, NONE"
	})
	@DisplayName("성별과 혈색소 수치에 따라 상태가 올바르게 판별된다.")
	void of(Double value, Gender gender, HemoglobinStatus expected) {
		// when
		HemoglobinStatus status = HemoglobinStatus.of(value, gender);

		// then
		assertThat(status).isEqualTo(expected);
	}
}
