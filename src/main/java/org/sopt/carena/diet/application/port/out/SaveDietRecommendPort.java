package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietRecommend;

import java.util.List;

public interface SaveDietRecommendPort {
    DietRecommend save(DietRecommend dietRecommend);
    List<DietRecommend> saveAll(List<DietRecommend> dietRecommends);
}
