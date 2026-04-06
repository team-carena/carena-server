package org.sopt.carena.healthreport.domain.status.liver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sopt.carena.member.domain.Gender;

class LiverStatusTest {

	@ParameterizedTest
	@CsvSource({
			"40.0, NORMAL",
			"40.1, AST_BORDERLINE",
			"50.0, AST_BORDERLINE",
			"50.1, AST_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("AST 수치에 따라 상태가 올바르게 판별된다.")
	void astStatus(Double value, AstStatus expected) {
		assertThat(AstStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"35.0, NORMAL",
			"35.1, ALT_BORDERLINE",
			"45.0, ALT_BORDERLINE",
			"45.1, ALT_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("ALT 수치에 따라 상태가 올바르게 판별된다.")
	void altStatus(Double value, AltStatus expected) {
		assertThat(AltStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"63.0, MALE, NORMAL",
			"63.1, MALE, GAMMA_GTP_BORDERLINE",
			"77.0, MALE, GAMMA_GTP_BORDERLINE",
			"77.1, MALE, GAMMA_GTP_SUSPICIOUS",
			"35.0, FEMALE, NORMAL",
			"35.1, FEMALE, GAMMA_GTP_BORDERLINE",
			"45.0, FEMALE, GAMMA_GTP_BORDERLINE",
			"45.1, FEMALE, GAMMA_GTP_SUSPICIOUS",
			", MALE, NONE",
			", FEMALE, NONE"
	})
	@DisplayName("성별과 Gamma-GTP 수치에 따라 상태가 올바르게 판별된다.")
	void gammaGtpStatus(Double value, Gender gender, GammaGtpStatus expected) {
		assertThat(GammaGtpStatus.of(value, gender)).isEqualTo(expected);
	}
}
