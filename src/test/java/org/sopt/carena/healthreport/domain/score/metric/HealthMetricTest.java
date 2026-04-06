package org.sopt.carena.healthreport.domain.score.metric;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sopt.carena.healthreport.domain.score.ScoreItem;
import org.sopt.carena.member.domain.Gender;

class HealthMetricTest {

	@ParameterizedTest
	@CsvSource({
			"22.0, MALE, 0, 100", // 정상
			"25.0, MALE, 1, 80",  // 상방 이탈 (25.0 - 24.9 = 0.1, unit 5.0 -> step 1)
			"18.4, MALE, 1, 80",  // 하방 이탈 (18.5 - 18.4 = 0.1, unit 5.0 -> step 1)
			"35.0, MALE, 3, 40",  // 큰 이탈 (35.0 - 24.9 = 10.1, unit 5.0 -> step 3)
			", MALE, 0, 100"      // null 케이스
	})
	@DisplayName("BMI 점수 계산이 정확하게 수행된다.")
	void bmiScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.BMI.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"89.9, MALE, 0, 100",
			"90.0, MALE, 1, 80",
			"84.9, FEMALE, 0, 100",
			"85.0, FEMALE, 1, 80",
			"87.0, MALE, 0, 100",   // 남성 기준(90) 미달 -> 정상
			"87.0, FEMALE, 1, 80",  // 여성 기준(85) 초과 -> 1단계 이탈
			", MALE, 0, 100"        // null 케이스
	})
	@DisplayName("허리둘레 점수 계산이 성별에 따라 정확하게 수행된다.")
	void waistScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.WAIST_CIRCUMFERENCE.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"13.0, MALE, 0, 100",
			"12.9, MALE, 1, 80",
			"12.0, FEMALE, 0, 100",
			"11.9, FEMALE, 1, 80",
			"12.5, MALE, 1, 80",    // 남성 기준(13.0) 미달 -> 1단계 이탈
			"12.5, FEMALE, 0, 100", // 여성 기준(12.0) 충족 -> 정상
			", MALE, 0, 100"        // null 케이스
	})
	@DisplayName("혈색소 점수 계산이 성별에 따라 정확하게 수행된다.")
	void hemoglobinScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.HEMOGLOBIN.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"99.9, MALE, 0, 100",
			"100.0, MALE, 1, 80", // upperExclusive=true 이므로 100.0 >= 100.0 에서 deviation 발생 (1e-9)
			"124.9, MALE, 1, 80",
			"125.0, MALE, 2, 60", // (25.0 + 1e-9) / 25.0 = 1.00000000004 -> step 2
			"125.1, MALE, 2, 60",
			", MALE, 0, 100"      // null 케이스
	})
	@DisplayName("공복혈당 점수 계산이 정확하게 수행된다.")
	void fastingGlucoseScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.FASTING_GLUCOSE.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"1.5, MALE, 0, 100",
			"1.6, MALE, 1, 80",
			"1.79, MALE, 1, 80",
			"1.8, MALE, 2, 60",  // (1.8 - 1.5) = 0.30000000000000004 -> step 2 (due to double precision)
			"1.9, MALE, 2, 60",
			", MALE, 0, 100"     // null 케이스
	})
	@DisplayName("혈청 크레아티닌 점수 계산이 정확하게 수행된다.")
	void creatinineScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.SERUM_CREATININE.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"60.0, MALE, 0, 100",
			"59.9, MALE, 1, 80",  // (60.0 - 59.9) / 15.0 = 0.006 -> step 1
			"45.0, MALE, 1, 80",  // (60.0 - 45.0) / 15.0 = 1.0 -> step 1
			"44.9, MALE, 2, 60",  // (60.0 - 44.9) / 15.0 = 1.006 -> step 2
			", MALE, 0, 100"      // null 케이스
	})
	@DisplayName("신사구체여과율(eGFR) 점수 계산이 정확하게 수행된다.")
	void egfrScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.EGFR.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"40.0, MALE, 0, 100",
			"40.1, MALE, 1, 80",
			"60.0, MALE, 1, 80",
			"60.1, MALE, 2, 60",
			", MALE, 0, 100"      // null 케이스
	})
	@DisplayName("AST 점수 계산이 정확하게 수행된다.")
	void astScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.AST.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"35.0, MALE, 0, 100",
			"35.1, MALE, 1, 80",
			"55.0, MALE, 1, 80",
			"55.1, MALE, 2, 60",
			", MALE, 0, 100"      // null 케이스
	})
	@DisplayName("ALT 점수 계산이 정확하게 수행된다.")
	void altScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.ALT.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource({
			"63.0, MALE, 0, 100",
			"63.1, MALE, 1, 80",
			"35.0, FEMALE, 0, 100",
			"35.1, FEMALE, 1, 80",
			"50.0, MALE, 0, 100",   // 남성 기준(63) 미달 -> 정상
			"50.0, FEMALE, 1, 80",  // 여성 기준(35) 초과 -> 1단계 이탈
			", MALE, 0, 100"        // null 케이스
	})
	@DisplayName("Gamma-GTP 점수 계산이 성별에 따라 정확하게 수행된다.")
	void gammaGtpScore(Double value, Gender gender, int expectedStep, int expectedScore) {
		ScoreItem result = HealthMetric.GAMMA_GTP.calculateResult(value, gender);
		assertThat(result.getStep()).isEqualTo(expectedStep);
		assertThat(result.getScore()).isEqualTo(expectedScore);
	}
}
