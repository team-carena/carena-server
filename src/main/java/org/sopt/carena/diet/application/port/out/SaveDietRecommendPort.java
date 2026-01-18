package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietRecommend;

public interface SaveDietRecommendPort {
    DietRecommend save(DietRecommend dietRecommend);
}
