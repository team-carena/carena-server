package org.sopt.carena.healthreport.domain.status.measurement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BloodPressureStatusTest {

	@ParameterizedTest(name = "수축기 {0}, 이완기 {1} -> {2}")
	@CsvSource({
			"119, 79, NORMAL",
			"120, 79, PRE_HYPERTENSION",
			"119, 80, PRE_HYPERTENSION",
			"139, 89, PRE_HYPERTENSION",
			"140, 89, HYPERTENSION",
			"139, 90, HYPERTENSION",
			"140, 90, HYPERTENSION",
			"140, , HYPERTENSION",
			", 90, HYPERTENSION",
			"120, , PRE_HYPERTENSION",
			", 80, PRE_HYPERTENSION",
			"119, , NORMAL",
			", 79, NORMAL"
	})
	@DisplayName("수축기와 이완기 혈압 수치에 따라 상태가 올바르게 판별된다.")
	void of(Integer systolic, Integer diastolic, BloodPressureStatus expected) {
		// when
		BloodPressureStatus status = BloodPressureStatus.of(systolic, diastolic);

		// then
		assertThat(status).isEqualTo(expected);
	}

	@Test
	@DisplayName("둘 다 null이면 NONE 상태를 반환한다.")
	void ofNull() {
		assertThat(BloodPressureStatus.of(null, null)).isEqualTo(BloodPressureStatus.NONE);
	}

	@ParameterizedTest(name = "수축기 {0} -> {1}")
	@CsvSource({
			"119, NORMAL",
			"120, PRE_HYPERTENSION",
			"140, HYPERTENSION"
	})
	@DisplayName("수축기 혈압만 있을 때 상태가 올바르게 판별된다.")
	void ofSystolicBp(Integer systolic, BloodPressureStatus expected) {
		assertThat(BloodPressureStatus.ofSystolicBp(systolic)).isEqualTo(expected);
	}

	@ParameterizedTest(name = "이완기 {0} -> {1}")
	@CsvSource({
			"79, NORMAL",
			"80, PRE_HYPERTENSION",
			"90, HYPERTENSION"
	})
	@DisplayName("이완기 혈압만 있을 때 상태가 올바르게 판별된다.")
	void ofDiastolicBp(Integer diastolic, BloodPressureStatus expected) {
		assertThat(BloodPressureStatus.ofDiastolicBp(diastolic)).isEqualTo(expected);
	}
}
