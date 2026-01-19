package org.sopt.carena.diet.application.port.out;

//위치 여기 맞는지 확인
import org.sopt.carena.healthreport.domain.HealthReport;

/**
 * 최신 건강검진 조회 포트
 */
public interface LoadLatestHealthReportPort {
    /**
     * 회원의 가장 최근 건강검진 ID 조회
     */
    HealthReport findLatestByMemberId(Long memberId);
}