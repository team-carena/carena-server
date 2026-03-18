package org.sopt.carena.healthreport.domain.status.dyslipidemia;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DyslipidemiaStatusTest {

	@ParameterizedTest
	@CsvSource({
			"199.9, NORMAL",
			"200.0, CHOLESTEROL_BORDERLINE",
			"239.9, CHOLESTEROL_BORDERLINE",
			"240.0, CHOLESTEROL_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("총 콜레스테롤 수치에 따라 상태가 올바르게 판별된다.")
	void cholesterolStatus(Double value, CholesterolStatus expected) {
		assertThat(CholesterolStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"60.0, NORMAL",
			"40.0, HDL_BORDERLINE",
			"59.9, HDL_BORDERLINE",
			"39.9, HDL_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("HDL 콜레스테롤 수치에 따라 상태가 올바르게 판별된다.")
	void hdlStatus(Double value, HdlStatus expected) {
		assertThat(HdlStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"129.9, NORMAL",
			"130.0, LDL_BORDERLINE",
			"159.9, LDL_BORDERLINE",
			"160.0, LDL_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("LDL 콜레스테롤 수치에 따라 상태가 올바르게 판별된다.")
	void ldlStatus(Double value, LdlStatus expected) {
		assertThat(LdlStatus.from(value)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({
			"149.9, NORMAL",
			"150.0, TRIGLYCERIDE_BORDERLINE",
			"199.9, TRIGLYCERIDE_BORDERLINE",
			"200.0, TRIGLYCERIDE_SUSPICIOUS",
			", NONE"
	})
	@DisplayName("중성지방 수치에 따라 상태가 올바르게 판별된다.")
	void triglycerideStatus(Double value, TriglycerideStatus expected) {
		assertThat(TriglycerideStatus.from(value)).isEqualTo(expected);
	}
}
