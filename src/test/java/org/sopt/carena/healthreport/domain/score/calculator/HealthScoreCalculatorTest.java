package org.sopt.carena.healthreport.domain.score.calculator;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.healthreport.domain.score.HealthScore;
import org.sopt.carena.member.domain.Gender;

class HealthScoreCalculatorTest {

	private final HealthScoreCalculator calculator = new HealthScoreCalculator();

	@Test
	@DisplayName("모든 지표가 정상 범위일 때 건강 점수는 100점이다.")
	void calculatePerfectScore() {
		// given
		HealthReport report = HealthReport.builder()
				.gender(Gender.MALE)
				.height(170.0)
				.weight(70.0)
				.waistCircumference(85.0)
				.bmi(22.0)
				.systolicBloodPressure(110)
				.diastolicBloodPressure(70)
				.hemoglobin(14.0)
				.fastingGlucose(90.0)
				.serumCreatinine(1.0)
				.egfr(90.0)
				.ast(20.0)
				.alt(20.0)
				.gammaGtp(30.0)
				.build();

		// when
		HealthScore healthScore = calculator.calculate(report);

		// then
		assertThat(healthScore.getValue()).isEqualTo(100L);
	}

	@Test
	@DisplayName("일부 지표가 비정상일 때 가중치에 따라 점수가 감점된다.")
	void calculateMixedScore() {
		// given
		// 공복혈당(가중치 0.2)이 1단계 이탈 (100.0) -> 점수 80
		// 나머지는 정상 (점수 100)
		HealthReport report = HealthReport.builder()
				.gender(Gender.MALE)
				.waistCircumference(85.0)
				.bmi(22.0)
				.systolicBloodPressure(110)
				.diastolicBloodPressure(70)
				.hemoglobin(14.0)
				.fastingGlucose(100.0) // 1단계 이탈
				.serumCreatinine(1.0)
				.egfr(90.0)
				.ast(20.0)
				.alt(20.0)
				.gammaGtp(30.0)
				.build();

		// when
		HealthScore healthScore = calculator.calculate(report);

		// then
		// 가중치 합산: FastingGlucose(0.2*80) + Others(0.8*100) = 16 + 80 = 96
		// BloodPressurePolicy 가중치: SYS_WEIGHT=0.12, DIA_WEIGHT=0.08 (둘 다 정상 100)
		// ... 상세 계산은 Metric의 가중치 합에 따름
		assertThat(healthScore.getValue()).isLessThan(100L);
		assertThat(healthScore.getValue()).isGreaterThan(0L);
	}

	@Test
	@DisplayName("데이터가 전혀 없을 때 건강 점수는 0점이다.")
	void calculateEmptyScore() {
		// given
		HealthReport report = HealthReport.builder()
				.gender(Gender.MALE)
				.build();

		// when
		HealthScore healthScore = calculator.calculate(report);

		// then
		assertThat(healthScore.getValue()).isEqualTo(0L);
	}

	@Test
	@DisplayName("동일한 수치라도 성별 기준에 따라 최종 건강 점수가 다를 수 있다.")
	void calculateGenderDifference() {
		// given
		// 허리둘레 87.0은 남성에게는 정상(100점)이나 여성에게는 비정상(80점)
		// 나머지는 모두 정상인 동일한 데이터
		Double commonWaist = 87.0;

		HealthReport maleReport = HealthReport.builder()
				.gender(Gender.MALE)
				.waistCircumference(commonWaist)
				.bmi(22.0)
				.systolicBloodPressure(110)
				.diastolicBloodPressure(70)
				.hemoglobin(14.0)
				.fastingGlucose(90.0)
				.serumCreatinine(1.0)
				.egfr(90.0)
				.ast(20.0)
				.alt(20.0)
				.gammaGtp(30.0)
				.build();

		HealthReport femaleReport = HealthReport.builder()
				.gender(Gender.FEMALE)
				.waistCircumference(commonWaist)
				.bmi(22.0)
				.systolicBloodPressure(110)
				.diastolicBloodPressure(70)
				.hemoglobin(13.0) // 여성에게 정상
				.fastingGlucose(90.0)
				.serumCreatinine(1.0)
				.egfr(90.0)
				.ast(20.0)
				.alt(20.0)
				.gammaGtp(20.0) // 여성에게 정상
				.build();

		// when
		HealthScore maleScore = calculator.calculate(maleReport);
		HealthScore femaleScore = calculator.calculate(femaleReport);

		// then
		// 남성은 허리둘레 87이 정상이므로 100점
		assertThat(maleScore.getValue()).isEqualTo(100L);
		// 여성은 허리둘레 87이 정상이 아니므로 100점보다 낮음
		assertThat(femaleScore.getValue()).isLessThan(100L);
	}
}
