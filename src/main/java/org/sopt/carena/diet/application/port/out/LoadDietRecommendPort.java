package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietRecommend;

import java.util.Optional;

public interface LoadDietRecommendPort {
    Optional<DietRecommend> findLatestByMemberId(Long memberId);
}
